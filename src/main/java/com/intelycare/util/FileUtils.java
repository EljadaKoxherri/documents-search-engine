package com.intelycare.util;

import com.intelycare.common.MessageFormatter;
import com.intelycare.exception.BadConfigException;

import java.io.File;

public class FileUtils {

    public static File getFile(String filePath) throws BadConfigException {
        File file = new File(filePath);
        if (!file.isFile()) {
            throw new BadConfigException(MessageFormatter.getMessage("app.config.file.error"));
        } else {
            return file;
        }
    }
}
