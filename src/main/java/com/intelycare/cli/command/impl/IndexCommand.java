package com.intelycare.cli.command.impl;

import com.intelycare.cli.command.Command;
import com.intelycare.common.MessageFormatter;
import com.intelycare.model.CommandArguments;
import com.intelycare.model.DocumentModel;
import com.intelycare.service.SearchEngineService;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public class IndexCommand implements Command {

    private SearchEngineService luceneService;

    public IndexCommand(SearchEngineService luceneService) {
        this.luceneService = luceneService;
    }

    @Override
    public String getCommandName() {
        return MessageFormatter.getMessage("app.command.index");
    }

    @Override
    public String execute(CommandArguments commandArguments) throws Exception {
        validateCommandArguments(commandArguments);
        DocumentModel documentModel = DocumentModel.builder()
                .documentId(Integer.valueOf(commandArguments.getCommandOptions()[1]))
                .tokens(Arrays.asList(commandArguments.getCommandOptions()))
                .build();

        luceneService.indexDocument(documentModel);

        return successMessage(documentModel.getDocumentId());
    }

    private String successMessage(Integer documentId) {
        return String.format(MessageFormatter.getMessage("app.command.index.response"), getCommandName(), documentId);
    }

    private void validateCommandArguments(CommandArguments commandArguments) throws Exception {
        try {
            if (!commandArguments.getCommandName().equals(getCommandName())) {
                throw new IllegalArgumentException(MessageFormatter.getMessage("app.command.missing.error"));
            }

            Integer.parseInt(commandArguments.getCommandOptions()[1]);

            Arrays.stream(commandArguments.getCommandOptions()).forEach(token -> {
                if(!StringUtils.isAlphanumeric(token)) {
                   throw new IllegalArgumentException(MessageFormatter.getMessage("app.command.index.non.alphanumeric"));
                }
            });
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(MessageFormatter.getMessage("app.command.index.docID_error"));
        }

    }
}
