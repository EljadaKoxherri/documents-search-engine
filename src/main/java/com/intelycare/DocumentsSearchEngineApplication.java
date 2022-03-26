package com.intelycare;

import com.intelycare.cli.view.View;
import com.intelycare.cli.view.impl.CLISearchEngineView;
import com.intelycare.common.ContainerConfig;
import org.picocontainer.PicoContainer;

public class DocumentsSearchEngineApplication {

    public static void main(String[] args) {
        PicoContainer container = ContainerConfig.INSTANCE.getContainer();
        View view = container.getComponent(CLISearchEngineView.class);
        view.show();
    }

}
