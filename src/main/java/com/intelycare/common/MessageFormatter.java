package com.intelycare.common;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageFormatter {
    INSTANCE;

    public static ResourceBundle messages;

    static {
        Locale locale = new Locale(ApplicationProperties.APPLICATION_LANGUAGE, ApplicationProperties.APPLICATION_COUNTRY);
        messages = ResourceBundle.getBundle("messages", locale);
    }

    public static String getMessage(String messageKey) {
        return messages.getString(messageKey);
    }
}
