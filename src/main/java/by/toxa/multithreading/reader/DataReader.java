package by.toxa.multithreading.reader;

import by.toxa.multithreading.exception.MultithreadingException;

import java.util.List;

public interface DataReader {

    public List<String> readData(String path) throws MultithreadingException;
}
