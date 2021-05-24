package by.toxa.multithreading.entity;

import java.util.concurrent.TimeUnit;

public class Truck extends Thread implements Vehicle {
    private static final int area = 19;
    private static final int weight = 20000;

    @Override
    public void run() {
        Ferry ferry = Ferry.getInstance();
        boolean load = false;
        while (!load) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            load = ferry.loadVehicle(this);
        }
    }

    @Override
    public int getArea() {
        return area;
    }

    @Override
    public int getWeight() {
        return weight;
    }
}
