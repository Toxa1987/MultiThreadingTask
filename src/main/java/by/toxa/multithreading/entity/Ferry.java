package by.toxa.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Ferry {
    private static final Logger logger = LogManager.getLogger();
    private static AtomicInteger area;
    private static AtomicInteger bearingCapacity;
    private static ReentrantLock lock = new ReentrantLock(true);
    private static Ferry instance;
    private static AtomicBoolean isInstanceHas = new AtomicBoolean(false);
    private AtomicInteger freeArea;
    private AtomicInteger freeBearingCapacity;
    private Queue<Vehicle> ferryQueue;
    private AtomicBoolean isLoading = new AtomicBoolean(true);

    private Ferry() {
        ferryQueue = new ArrayDeque<>();
        if (area == null || bearingCapacity == null) {
            ResourceBundle rb = ResourceBundle.getBundle("property.default_ferry");
            String ferryArea = rb.getString("default_ferry_area");
            String ferryBearingCapacity = rb.getString("default_ferry_bearing_capacity");
            Ferry.ferryConfiguration(Integer.parseInt(ferryArea), Integer.parseInt(ferryBearingCapacity));
            logger.info("Created ferry with default parameters area = " + area + " bearing capacity = " + bearingCapacity);
        }
        freeArea = new AtomicInteger(area.get());
        freeBearingCapacity = new AtomicInteger(bearingCapacity.get());
    }

    public static Ferry getInstance() {
        if (!isInstanceHas.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new Ferry();
                    isInstanceHas.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public static void ferryConfiguration(int ferryArea, int ferryBearingCapacity) {
        logger.info("Create ferry with parameters area = " + ferryArea + " bearing capacity = " + ferryBearingCapacity);
        bearingCapacity = new AtomicInteger(ferryBearingCapacity);
        area = new AtomicInteger(ferryArea);
    }

    public boolean loadVehicle(Vehicle vehicle) {
        lock.lock();
        logger.info("Try load: " + Thread.currentThread().getName());
        boolean result = false;
        try {
            if (isLoading.get()) {
                if (vehicle.getArea() < freeArea.get() & vehicle.getWeight() < freeBearingCapacity.get()) {
                    int tempFreeArea = freeArea.get() - vehicle.getArea();
                    int tempFreeBearingCapacity = freeBearingCapacity.get() - vehicle.getWeight();
                    freeArea.getAndSet(tempFreeArea);
                    freeBearingCapacity.getAndSet(tempFreeBearingCapacity);
                    result = ferryQueue.offer(vehicle);
                    System.out.println(Thread.currentThread().getName() + " loaded");
                    if (tempFreeArea < (int) ((area.get() * 0.1)) ||
                            tempFreeBearingCapacity < (int) ((bearingCapacity.get() * 0.1))) {
                        isLoading.getAndSet(false);
                    }
                } else {
                    isLoading.getAndSet(false);
                }
            }
        } finally {
            lock.unlock();
        }

        return result;
    }

    public boolean runToUnload() {
        lock.lock();
        boolean result = false;
        try {
            if (!isLoading.get()) {
                ferryQueue.clear();
                isLoading.getAndSet(true);
                freeArea = new AtomicInteger(area.get());
                freeBearingCapacity = new AtomicInteger(bearingCapacity.get());
                result = true;
                logger.info("Ferry is unload");
            }
        } finally {
            lock.unlock();
        }
        return result;
    }
}
