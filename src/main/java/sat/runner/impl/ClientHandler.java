package sat.runner.impl;

import sat.exception.ClientDisconnectedException;
import sat.exception.UnknownCommandException;
import sat.factory.Command;
import sat.factory.CommandFactory;
import sat.factory.CommandFactoryBuilder;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.UUID;

public class ClientHandler implements Runnable {

    private final CommandFactoryBuilder commandFactoryBuilder;
    private final Socket socket;
    private UUID sessionID;

    public ClientHandler(CommandFactoryBuilder commandFactoryBuilder, Socket socket) {
        this.commandFactoryBuilder = commandFactoryBuilder;
        this.socket = socket;
        this.sessionID = UUID.randomUUID();

    }

    public void run() {
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            CommandFactory commandFactory = commandFactoryBuilder.buildCommandFactoryForUser(bufferedReader, bufferedWriter, sessionID);
            bufferedWriter.append("Добро пожаловать в SAT").append("\n");
            bufferedWriter.append("Для получения помощи по командам используйте команду help.").append("\n");
            bufferedWriter.flush();

            String command;
            boolean started = true;
            while (started) {
                try {
                    bufferedWriter.append("Введите команду: ");
                    bufferedWriter.flush();
                    command = bufferedReader.readLine();
                    Command anyCommand = commandFactory.getCommand(command);
                    anyCommand.execute();
                } catch (UnknownCommandException e) {
                    bufferedWriter.write("Команда не распознана. Проверьте список доступных команд и попробуйте снова.\n");
                    bufferedWriter.flush();
                } catch (ClientDisconnectedException e) {
                    System.out.println("Клиент " + socket.getRemoteSocketAddress() + " закрыл соединение");
                    started = false;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (Objects.nonNull(socket)) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
