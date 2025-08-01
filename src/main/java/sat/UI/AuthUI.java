package sat.UI;

import sat.exception.ValidateException;

import java.io.IOException;

public interface AuthUI {
    String requestLogin() throws IOException, ValidateException;

    String requestPassword() throws IOException;

    void showLoginSuccess(String login) throws IOException;

    void showValidationError(String message) throws IOException;

    void showUserNotFound(String message) throws IOException;

    void showMustLogoutMessage() throws IOException;

    void showMustLoginMessage() throws IOException;

    void showLogoutMessage(String login) throws IOException;

    void showInvalidPasswordMessage(String password) throws IOException;

    void showGoodbyeMessage() throws IOException;
}
