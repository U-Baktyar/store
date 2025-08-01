package sat.runner;

public enum EnumCommand {
    HELP_COMMAND("help", "вывод списка комманд"),
    EXIT_COMMAND("exit", "выход из системы"),
    REGISTER_COMMAND("register", "регистрация"),
    LOGIN_COMMAND("login", "вход в систему"),
    LOGOUT_COMMAND("logout",  "выйти"),
    ALL_PRODUCTS_COMMAND("all_products", "покозать все продукты"),
    GET_ALL_PRODUCTS_BY_CATEGORY_COMMAND("get_all_products_by_category", "получить все товары по категории"),
    FIND_AND_DISPLAY_PRODUCT_BY_NAME_COMMAND("find_and_display", "получить товар по названии");

    private final String command;
    private final String description;

    EnumCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static EnumCommand getCommand(String command) {
        for (EnumCommand enumCommand: EnumCommand.values()){
            if (enumCommand.command.equals(command)){
                return enumCommand;
            }
        }
        return null;
    }

    public static String get_command_info(){
        StringBuilder allCommands = new StringBuilder();
        for (EnumCommand commandEnum : EnumCommand.values()) {
            String str = commandEnum.command + " - " + commandEnum.description + "\n";
            allCommands.append(str);
        }
        return allCommands.toString();
    }
}
