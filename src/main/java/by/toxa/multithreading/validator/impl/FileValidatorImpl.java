package by.toxa.multithreading.validator.impl;

import by.toxa.multithreading.validator.FileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileValidatorImpl implements FileValidator {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean validateFilePath(String path) {
        logger.info("Method of validation filepath called with next options: " + path);
        if (path == null || path.isEmpty() || path.trim().isEmpty()) {
            return false;
        }
        boolean flag = false;
        File file = new File(path);
        if (file.exists()) {
            if (file.length() > 0) {
                flag = true;
            }
        }
        return flag;
    }

}
