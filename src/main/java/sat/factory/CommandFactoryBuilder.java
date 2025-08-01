package sat.factory;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.UUID;

public interface CommandFactoryBuilder {
    CommandFactory buildCommandFactoryForUser(BufferedReader reader, BufferedWriter writer, UUID uuid);
}
