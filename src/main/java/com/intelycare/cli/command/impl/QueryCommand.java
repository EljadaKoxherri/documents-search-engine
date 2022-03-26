package com.intelycare.cli.command.impl;

import com.intelycare.cli.command.Command;
import com.intelycare.common.MessageFormatter;
import com.intelycare.model.CommandArguments;
import com.intelycare.service.SearchEngineService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class QueryCommand implements Command {

    private SearchEngineService luceneService;

    public QueryCommand(SearchEngineService luceneService) {
        this.luceneService = luceneService;
    }

    @Override
    public String getCommandName() {
        return MessageFormatter.getMessage("app.command.query");
    }

    @Override
    public String execute(CommandArguments commandArguments) throws Exception {
        validateCommandArguments(commandArguments);

        List<Integer> integers = luceneService.queryDocuments(IntStream.range(1, commandArguments.getCommandOptions().length)
                .mapToObj(i -> commandArguments.getCommandOptions()[i]).collect(joining("")));

        if(integers.isEmpty()) {
            return MessageFormatter.getMessage("app.command.query.response.error");
        }
        return successMessage(integers);
    }

    private String successMessage(List<Integer> ids) {
        return String.format(MessageFormatter.getMessage("app.command.query.response"), getCommandName(), ids.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }

    private void validateCommandArguments(CommandArguments commandArguments) throws Exception {
        if (!commandArguments.getCommandName().equals(getCommandName())) {
            throw new IllegalArgumentException(MessageFormatter.getMessage("app.command.missing.error"));
        }
    }


}
