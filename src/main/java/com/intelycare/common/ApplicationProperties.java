package com.intelycare.common;

import com.intelycare.util.FileUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public enum ApplicationProperties {
    ;

    private static PropertiesConfiguration applicationProperties;
    private static final String FILENAME = "src/main/resources/config.properties";
    private static String ENV = System.getenv("environment");

    static {
        try {
            if (ENV == null || ENV.isEmpty()) {
                ENV = "dev";
            }

            applicationProperties = new PropertiesConfiguration(FileUtils.getFile(FILENAME));
            applicationProperties.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static final String APPLICATION_LANGUAGE = applicationProperties.getString(ENV + ".app.language", "EN");
    public static final String APPLICATION_COUNTRY = applicationProperties.getString(ENV + ".app.country", "US");

}
