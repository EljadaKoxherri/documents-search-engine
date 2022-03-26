package com.intelycare.common;

import com.intelycare.cli.CommandFactory;
import com.intelycare.cli.Processor;
import com.intelycare.cli.command.impl.ExitCommand;
import com.intelycare.cli.command.impl.IndexCommand;
import com.intelycare.cli.command.impl.QueryCommand;
import com.intelycare.cli.view.impl.CLISearchEngineView;
import com.intelycare.service.LuceneIndexServiceImpl;
import com.intelycare.service.LuceneSearchServiceImpl;
import com.intelycare.service.SearchEngineServiceImpl;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;

public enum ContainerConfig {
    INSTANCE;

    private final PicoContainer picoContainer;

    ContainerConfig() {
        final DefaultPicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(PicoContainer.class, pico);
        pico.addComponent(LuceneIndexServiceImpl.class);
        pico.addComponent(LuceneSearchServiceImpl.class);
        pico.addComponent(SearchEngineServiceImpl.class);
        pico.addComponent(CLISearchEngineView.class);
        pico.addComponent(IndexCommand.class);
        pico.addComponent(QueryCommand.class);
        pico.addComponent(ExitCommand.class);
        pico.addComponent(CommandFactory.class);
        pico.addComponent(Processor.class);
        picoContainer = pico;
    }

    public PicoContainer getContainer() {
        return picoContainer;
    }
}
