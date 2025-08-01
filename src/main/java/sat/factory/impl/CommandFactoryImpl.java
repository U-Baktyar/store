package sat.factory.impl;


import sat.controller.AuthController;
import sat.controller.InfoController;
import sat.controller.ProductController;
import sat.controller.RegistrationUserController;
import sat.exception.UnknownCommandException;
import sat.exception.ValidateException;
import sat.factory.Command;
import sat.factory.CommandFactory;
import sat.runner.EnumCommand;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class CommandFactoryImpl implements CommandFactory {
    private Map<EnumCommand, Command> commands;
    private final InfoController infoController;
    private final AuthController authController;
    private final RegistrationUserController registrationUserController;
    private final ProductController productController;


    public CommandFactoryImpl(
            InfoController infoController,
            AuthController authController,
            RegistrationUserController registrationUserController,
            ProductController productController
    ) {
        this.infoController = infoController;
        this.authController = authController;
        this.registrationUserController = registrationUserController;
        this.productController = productController;
        this.commands = new EnumMap<>(EnumCommand.class);
        this.initCommands();
    }

    private void initCommands(){
        commands.put(EnumCommand.HELP_COMMAND, () -> {
            try {
                infoController.printInfoCommands();
            }catch (IOException e) {
                System.out.println("Ошибка при выполнении входа: " + e.getMessage());
            }

        });
        commands.put(EnumCommand.LOGIN_COMMAND, () -> {
            try {
                authController.login();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении входа: " + e.getMessage());
            }
        });
        commands.put(EnumCommand.EXIT_COMMAND,  () -> {
            try {
                authController.exit();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении команды выхода: " + e.getMessage());
            }
        });
        commands.put(EnumCommand.REGISTER_COMMAND, () -> {
            try {
                registrationUserController.register();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении регистрации: " + e.getMessage());
            }
        });
        commands.put(EnumCommand.LOGOUT_COMMAND, () -> {
            try {
                authController.logout();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении выхода: " + e.getMessage());
            }
        });
        commands.put(EnumCommand.ALL_PRODUCTS_COMMAND, () -> {
            try {
                productController.getAllProducts();
            }catch (IOException e) {
                System.out.println("Ошибка при получении всех продуктов: " + e.getMessage());
            }
        });


    }
    public Command getCommand(String command) throws UnknownCommandException {
        EnumCommand enumCommand = EnumCommand.getCommand(command);
        if(Objects.isNull(enumCommand) || !commands.containsKey(enumCommand)){
            throw new UnknownCommandException();
        }
        return commands.get(enumCommand);
    }
}
