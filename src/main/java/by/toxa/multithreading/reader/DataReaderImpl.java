package by.toxa.multithreading.reader;

import by.toxa.multithreading.exception.MultithreadingException;
import by.toxa.multithreading.validator.impl.FileValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReaderImpl implements DataReader {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public List<String> readData(String path) throws MultithreadingException {
        logger.info("Read data with path: " + path);
        if (!new FileValidatorImpl().validateFilePath(path)) {
            throw new MultithreadingException("Incorrect path or file is not exist");
        }
        ArrayList<String> lines;
        Path pathFile = Paths.get(path);
        try (Stream<String> lineStream = Files.lines(pathFile)) {
            lines = lineStream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new MultithreadingException("Reading file is fail ",e);
        }
        return  lines;

    }
}
