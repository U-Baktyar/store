package sat.configuration;

import java.lang.annotation.ElementType;
import java.lang.reflect.Executable;
import java.util.List;

public class ComponentDefinition <T extends Executable> {
    private final Class<?> kayClass;
    private final Class<?> componentClass;
    private final T constructorClassOrMethod;
    private final List<Class<?>> argumentsType;
    private final ElementType elementType;


    public ComponentDefinition(
            Class<?> kayClass,
            Class<?> componentClass,
            T constructorClassOrMethod,
            List<Class<?>> argumentsType,
            ElementType elementType
    ) {
        this.kayClass = kayClass;
        this.componentClass = componentClass;
        this.constructorClassOrMethod = constructorClassOrMethod;
        this.argumentsType = argumentsType;
        this.elementType = elementType;
    }

    public Class<?> getKayClass() {
        return kayClass;
    }

    public Class<?> getComponentClass() {
        return componentClass;
    }

    public T getConstructorClassOrMethod() {
        return constructorClassOrMethod;
    }

    public List<Class<?>> getArgumentsType() {
        return argumentsType;
    }

    public ElementType getElementType() {
        return elementType;
    }

}
