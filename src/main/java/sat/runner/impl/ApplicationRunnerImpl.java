package sat.runner.impl;

import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;

import sat.factory.CommandFactoryBuilder;
import sat.runner.ApplicationRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final CommandFactoryBuilder commandFactoryBuilder;
    private final int port = 9999;

    @Autowired
    public ApplicationRunnerImpl(
            CommandFactoryBuilder commandFactoryBuilder
    ) {
        this.commandFactoryBuilder = commandFactoryBuilder;
    }

    @Override
    public void applicationRun() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Клиент подключился: " + socket.getRemoteSocketAddress());

                    Thread thread = new Thread(new ClientHandler(commandFactoryBuilder, socket));
                    thread.start();
                } catch (IOException e) {
                    System.err.println("Ошибка при приёме клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
