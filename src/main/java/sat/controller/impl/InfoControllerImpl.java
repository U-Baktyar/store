package sat.controller.impl;

import sat.UI.InfoUI;
import sat.controller.InfoController;
import sat.runner.EnumCommand;

import java.io.IOException;


public class InfoControllerImpl implements InfoController {

    public final InfoUI infoUI;


    public InfoControllerImpl(
            InfoUI infoUI
    ) {
        this.infoUI = infoUI;
    }

    @Override

    public void printInfoCommands() throws IOException{
        infoUI.showAllCommands(EnumCommand.get_command_info());
    }
}
