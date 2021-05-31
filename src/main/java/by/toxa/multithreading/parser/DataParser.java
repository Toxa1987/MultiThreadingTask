package by.toxa.multithreading.parser;

import java.util.List;

public interface DataParser {
    public void parse(List<String> list);

    public int getFerryBearingCapacity();

    public int getFerryArea();
}
