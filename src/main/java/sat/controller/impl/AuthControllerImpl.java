package sat.controller.impl;

import sat.UI.AuthUI;
import sat.controller.AuthController;
import sat.entity.user.User;
import sat.exception.ClientDisconnectedException;
import sat.exception.UserNotFoundException;
import sat.exception.ValidateException;
import sat.service.UserService;
import sat.session.SessionUser;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class AuthControllerImpl implements AuthController {

    private final UserService userService;
    private final AuthUI authUI;
    private final SessionUser sessionUser;
    private final UUID sessionId;

    public AuthControllerImpl(
            UserService userService,
            AuthUI authUI,
            SessionUser sessionUser,
            UUID sessionId
    ) {
        this.userService = userService;
        this.authUI = authUI;
        this.sessionUser = sessionUser;
        this.sessionId = sessionId;
    }

    @Override
    public void exit() throws IOException, ClientDisconnectedException {
        authUI.showGoodbyeMessage();
        throw new ClientDisconnectedException();
    }

    @Override
    public void login() throws IOException {
        if (sessionUser.isAuthenticated(sessionId)) {
            authUI.showMustLogoutMessage(); // уже вошёл
            return;
        }

        String login = "";
        try {
            login = authUI.requestLogin();
        } catch (ValidateException e) {
            authUI.showValidationError(e.getMessage());
            return;
        }

        try {
            User user = userService.getByLoginUser(login);
            if (Objects.nonNull(user)) {
                String password = authUI.requestPassword();
                if (user.getPassword().equals(password)) {
                    sessionUser.setAuthentication(sessionId, user);
                    authUI.showLoginSuccess(sessionUser.getAuthentication(sessionId).getLogin());
                    return;
                } else {
                    authUI.showInvalidPasswordMessage(password);
                }
            }
        } catch (UserNotFoundException e) {
            authUI.showUserNotFound(e.getMessage());
        }
    }

    @Override
    public void logout() throws IOException {
        if (!sessionUser.isAuthenticated(sessionId)) {
            authUI.showMustLoginMessage();
            return;
        }
        authUI.showLogoutMessage(sessionUser.getAuthentication(sessionId).getLogin());
        sessionUser.removeAuthentication(sessionId);
    }
}
