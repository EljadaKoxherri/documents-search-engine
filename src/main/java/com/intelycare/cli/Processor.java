package com.intelycare.cli;

import com.intelycare.cli.command.Command;
import com.intelycare.model.CommandArguments;

public class Processor {

    private CommandFactory commandFactory;

    public Processor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public String process(String commandLine) throws Exception {
        CommandArguments commandArguments = parseArguments(commandLine);
        Command command = commandFactory.createCommand(commandArguments.getCommandName());
        return command.execute(commandArguments);
    }

    private CommandArguments parseArguments(String commandLine) {
        String[] commands = commandLine.split(" ");
        return CommandArguments.builder()
                .commandName(commands[0])
                .commandOptions(commands)
                .build();
    }
}
