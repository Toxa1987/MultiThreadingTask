package by.toxa.multithreading;

import by.toxa.multithreading.entity.UnloadThread;
import by.toxa.multithreading.entity.Vehicle;
import by.toxa.multithreading.entity.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        List<Vehicle> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            boolean temp = random.nextBoolean();
            if (temp) {
                list.add(new Vehicle(VehicleType.CAR));
            } else {
                list.add(new Vehicle(VehicleType.TRUCK));
            }
        }
        Thread daemon = new UnloadThread();
        daemon.setDaemon(true);
        daemon.start();
        ExecutorService service = Executors.newFixedThreadPool(list.size());

        for (Vehicle v : list
        ) {
            service.execute(v);
        }
        service.shutdown();
    }
}
