package com.intelycare.cli;

import com.intelycare.cli.command.Command;
import com.intelycare.cli.command.impl.ExitCommand;
import com.intelycare.cli.command.impl.IndexCommand;
import com.intelycare.cli.command.impl.QueryCommand;
import com.intelycare.common.ContainerConfig;
import com.intelycare.common.MessageFormatter;
import com.intelycare.exception.CommandMissingException;
import org.picocontainer.PicoContainer;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        PicoContainer container = ContainerConfig.INSTANCE.getContainer();

        commands.put("index", container.getComponent(IndexCommand.class));
        commands.put("query", container.getComponent(QueryCommand.class));
        commands.put("exit", container.getComponent(ExitCommand.class));
    }

    public Command createCommand(String commandName) throws CommandMissingException {
        if (!commands.containsKey(commandName)) {
            throw new CommandMissingException(MessageFormatter.getMessage("app.command.missing.error"));
        }
        return commands.get(commandName);
    }
}
