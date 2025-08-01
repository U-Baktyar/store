package sat.UI.impl;

import sat.UI.InfoUI;
import sat.runner.EnumCommand;

import java.io.BufferedWriter;
import java.io.IOException;

public class InfoUIImpl implements InfoUI {
    private final BufferedWriter writer;

    public InfoUIImpl(
            BufferedWriter writer
    ) {
        this.writer = writer;
    }

    @Override
    public void showAllCommands(String commands) throws IOException {
        writer.append(EnumCommand.get_command_info()).append("\r\n");
        writer.flush();
    }
}
