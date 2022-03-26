package com.intelycare.cli.command;

import com.intelycare.model.CommandArguments;

public interface Command {
    String getCommandName();
    String execute(CommandArguments commandArguments) throws Exception;
}
