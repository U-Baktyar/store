package sat.configuration;

import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.configuration.annotation.ComponentMethod;
import sat.configuration.annotation.ComponentSource;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentContainer {
    public final Map<Class<?>, Object> components;
    private final Class<?> mainClass;
    private final String packageName;

    public ComponentContainer(Class<?> mainClass) {
        this.components = new HashMap<>();
        this.mainClass = mainClass;
        this.packageName = mainClass.getPackage().getName();
    }

    public <T> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    public void configure() {
        try {
            List<Class<?>> classes = this.getClasses();
            List<ComponentDefinition<?>> componentDefinitions = new LinkedList<>();

            for (Class<?> clazz : classes) {
                if (clazz.isAnnotation() || clazz.isInterface() || clazz.isEnum()) {
                    continue;
                }
                if (clazz.isAnnotationPresent(Component.class)) {
                    Constructor<?>[] constructors = clazz.getConstructors();
                    Constructor<?> constructorAutowired = null;
                    for (Constructor<?> constructor : constructors) {
                        if (constructor.isAnnotationPresent(Autowired.class)) {
                            constructorAutowired = constructor;
                        }
                    }

                    if (Objects.isNull(constructorAutowired)) {
                        System.out.println("\n[Ошибка] Не удалось сконфигурировать компонент: " + clazz.getName());
                        System.out.println("Не найден конструктор с @Autowired или он приватный");
                        System.exit(1);
                    }

                    List<Class<?>> interfaces = List.of(clazz.getInterfaces());
                    if (!interfaces.isEmpty()) {
                        for (Class<?> inter : interfaces) {
                            if (inter.getPackageName().startsWith(packageName)) {
                                componentDefinitions.add(
                                        new ComponentDefinition<Constructor<?>>(
                                                inter,
                                                clazz,
                                                constructorAutowired,
                                                List.of(constructorAutowired.getParameterTypes()),
                                                ElementType.TYPE
                                        )
                                );
                            }
                        }
                    } else {
                        componentDefinitions.add(
                                new ComponentDefinition<Constructor<?>>(
                                        clazz,
                                        clazz,
                                        constructorAutowired,
                                        List.of(constructorAutowired.getParameterTypes()),
                                        ElementType.TYPE
                                )
                        );
                    }
                }
                if (clazz.isAnnotationPresent(ComponentSource.class)) {
                    Arrays
                            .stream(clazz.getDeclaredMethods())
                            .filter(method -> method.isAnnotationPresent(ComponentMethod.class))  // Исправлено здесь
                            .forEach(method -> {
                                Class<?> returnType = method.getReturnType();
                                List<Class<?>> parameterTypes = List.of(method.getParameterTypes());
                                ComponentDefinition<?> componentDefinition = new ComponentDefinition<>(returnType, clazz, method, parameterTypes, ElementType.METHOD);
                                componentDefinitions.add(componentDefinition);
                            });
                }
            }

            List<Class<?>> configurableClasses = new LinkedList<>();
            while (!componentDefinitions.isEmpty()) {
                ComponentDefinition<?> definition = componentDefinitions.removeFirst();
                configureComponent(definition, componentDefinitions, configurableClasses);
            }

        } catch (Exception ex) {
            System.out.println("[Ошибка] Конфигурация провалена: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
    }



    private void configureComponent(ComponentDefinition<?> definition, List<ComponentDefinition<?>> componentDefinitions, List<Class<?>> configurableClasses) throws Exception {
        if(definition.getArgumentsType().isEmpty()){
            this.components.put(definition.getKayClass(), createComponent(definition));
            return;
        }
        configurableClasses.add(definition.getKayClass());
        List<Object> args = new ArrayList<>(definition.getArgumentsType().size());
        for (Class<?> param : definition.getArgumentsType()) {
            if(configurableClasses.contains(param)) {
                System.out.println("[Ошибка] Обнаружена циклическая зависимость...");
                String dependencyChain = configurableClasses.stream().map(Class::getName).collect(Collectors.joining(" -> "));
                System.out.println("Цепочка зависимости: " + dependencyChain);
                System.exit(1);
            }
            if(!components.containsKey(param)) {
                ComponentDefinition<?> dependency = retrieveDefinitionByKeyClass(param, componentDefinitions);
                configureComponent(dependency, componentDefinitions, configurableClasses);
            }
            args.add(this.components.get(param));
        }
        this.components.put(definition.getKayClass(), createComponent(definition, args.toArray()));
        configurableClasses.remove(definition.getKayClass());
    }

    private Object createComponent(ComponentDefinition<?> definition, Object ...args) throws Exception {
        if (definition.getElementType().equals(ElementType.METHOD)) {
            Method method = (Method) definition.getConstructorClassOrMethod();
            Object instanceToCallMethod = definition.getComponentClass().getConstructors()[0].newInstance();
            return method.invoke(instanceToCallMethod, args);
        }
        Constructor<?> constructor = (Constructor<?>) definition.getConstructorClassOrMethod();
        return constructor.newInstance(args);
    }

    private ComponentDefinition<?> retrieveDefinitionByKeyClass(Class<?> kayClass, List<ComponentDefinition<?>> componentDefinitions) {
        Optional<ComponentDefinition<?>> definition = componentDefinitions.stream().filter(def -> def.getKayClass().equals(kayClass)).findFirst();
        if(definition.isEmpty()) {
            System.out.println("[Ошибка] Не найден компонент в системе: " + kayClass.getName());
            System.exit(1);
        }
        componentDefinitions.remove(definition.get());
        return definition.get();
    }

    private List<Class<?>> getClasses() {
        List<Class<?>> classes = new LinkedList<>();
        String path = packageName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource == null) {
            System.out.println("Путь к директории не найден: " + path);
            return classes;
        }

        File directory = new File(resource.getFile());
        if (!directory.exists()) {
            System.out.println("Директория не существует: " + directory.getAbsolutePath());
            return classes;
        }

        collectClasses(directory, packageName, classes);
        return classes;
    }

    private void collectClasses(File dir, String currentPackage, List<Class<?>> classes) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                collectClasses(file, currentPackage + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Class.forName(currentPackage + '.' + className);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    System.out.println("Не удалось загрузить класс: " + currentPackage + "." + className);
                    e.printStackTrace();
                }
            }
        }
    }

}
