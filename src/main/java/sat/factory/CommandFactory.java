package sat.factory;

import sat.exception.UnknownCommandException;

@FunctionalInterface
public interface CommandFactory {
    Command getCommand(String command) throws UnknownCommandException;
}
