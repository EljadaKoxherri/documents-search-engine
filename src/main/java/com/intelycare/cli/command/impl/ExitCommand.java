package com.intelycare.cli.command.impl;

import com.intelycare.cli.command.Command;
import com.intelycare.common.MessageFormatter;
import com.intelycare.model.CommandArguments;

public class ExitCommand implements Command {

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public String execute(CommandArguments commandArguments) {
        System.out.println(MessageFormatter.getMessage("app.exit.message"));
        System.exit(1);
        return "";
    }

}
