package sat.controller;

import sat.exception.ClientDisconnectedException;

import java.io.IOException;

public interface AuthController {
    void exit() throws IOException, ClientDisconnectedException;
    void login() throws IOException;
    void logout() throws IOException;

}
