package com.intelycare.cli.view.impl;

import com.intelycare.cli.Processor;
import com.intelycare.cli.view.View;
import com.intelycare.common.MessageFormatter;

import java.util.Scanner;

public class CLISearchEngineView implements View {

    private Processor processor;

    public CLISearchEngineView(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void show() {
        System.out.println(MessageFormatter.getMessage("app.title"));
        printMenu();
        askForCommand();
    }

    private void printMenu() {
        System.out.println(MessageFormatter.getMessage("app.menu.text"));
    }

    private void askForCommand() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            try {
                System.out.print(MessageFormatter.getMessage("app.menu.command.text"));
                command = scanner.nextLine();
                String response = processor.process(command);
                printResponseMessage(response);
            } catch (Exception e) {
                handleError(e);
            }
        }
    }

    private void handleError(Exception e) {
        System.out.println(e.getMessage());
        printMenu();
    }

    private void printResponseMessage(String response) {
        System.out.println(response);
        System.out.println();
    }

}