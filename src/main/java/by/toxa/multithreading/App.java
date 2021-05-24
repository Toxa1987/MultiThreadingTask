package by.toxa.multithreading;

import by.toxa.multithreading.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<Vehicle> list = new ArrayList<>();
        list.add(new Car());
        list.add(new Truck());
        list.add(new Car());
        list.add(new Truck());
        list.add(new Car());
        list.add(new Car());
        list.add(new Truck());
        list.add(new Car());
        list.add(new Truck());

        Ferry.ferryConfiguration(34, 100000);
        Thread daemon = new UnloadThread();
        daemon.setDaemon(true);
        daemon.start();
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (Vehicle v : list
        ) {
            if (v.getClass() == Car.class) {
                service.execute(new Car());
            } else {
                service.execute(new Truck());
            }
        }

        service.shutdown();
    }
}
