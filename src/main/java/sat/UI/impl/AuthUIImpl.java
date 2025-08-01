package sat.UI.impl;

import sat.UI.AuthUI;
import sat.exception.ValidateException;
import sat.validation.UserValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class AuthUIImpl implements AuthUI {
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final UserValidation userValidation;

    public AuthUIImpl(
            BufferedReader reader,
            BufferedWriter writer,
            UserValidation userValidation
    ) {
        this.reader = reader;
        this.writer = writer;
        this.userValidation = userValidation;
    }

    @Override
    public String requestLogin() throws IOException, ValidateException {
        writer.write("Введите логин: ");
        writer.flush();
        String login = reader.readLine();
        return userValidation.checkLogin(login);
    }

    @Override
    public String requestPassword() throws IOException {
        writer.write("Введите пароль: ");
        writer.flush();
        return reader.readLine();
    }

    @Override
    public void showLoginSuccess(String login) throws IOException {
        writer.write("Добро пожаловать, " + login + "!\n");
        writer.flush();
    }

    @Override
    public void showValidationError(String message) throws IOException {
        writer.write("Ошибка: " + message + "\n");
        writer.flush();
    }

    @Override
    public void showUserNotFound(String message) throws IOException {
        writer.write("Пользователь не найден: " + message + "\n");
        writer.flush();
    }

    @Override
    public void showMustLogoutMessage() throws IOException {
        writer.write("Вы уже вошли в систему. Пожалуйста, сначала выйдите из неё.\n");
        writer.flush();
    }

    @Override
    public void showMustLoginMessage() throws IOException {
        writer.write("Необходимо войти в систему для выполнения этой команды.\n");
        writer.flush();
    }

    @Override
    public void showLogoutMessage(String login) throws IOException {
        writer.write("<<<<<< Выход из системы >>>>>>\n");
        writer.write("До свидания, " + login + "!\n");
        writer.flush();
    }

    @Override
    public void showInvalidPasswordMessage(String password) throws IOException {
        writer.write("Неверный пароль - " + password + ". Попробуйте снова.\n");
        writer.flush();
    }

    @Override
    public void showGoodbyeMessage() throws IOException {
        writer.write("До свидания!\n");
        writer.flush();
    }


}
