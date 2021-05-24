package by.toxa.multithreading.entity;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Ferry {
    private static AtomicInteger area;
    private static AtomicInteger bearingCapacity;
    private static ReentrantLock reentrantLock = new ReentrantLock(true);

    private static Ferry instance;
    private AtomicInteger freeArea;
    private AtomicInteger freeBearingCapacity;
    private Queue<Vehicle> ferryQueue;
    private AtomicBoolean isLoading = new AtomicBoolean(true);

    private Ferry() {
        ferryQueue = new ConcurrentLinkedQueue<>();
    }

    public static Ferry getInstance() {
        reentrantLock.lock();
        try {
            if (instance == null) {
                instance = new Ferry();
            }
        } finally {
            reentrantLock.unlock();
        }
        return instance;
    }

    public static void ferryConfiguration(int ferryArea, int ferryBearingCapacity) {
        bearingCapacity = new AtomicInteger(ferryBearingCapacity);
        area = new AtomicInteger(ferryArea);
    }

    public boolean loadVehicle(Vehicle vehicle) {
        reentrantLock.lock();
        if (freeBearingCapacity == null || freeArea == null) {
            freeArea = new AtomicInteger(area.get());
            freeBearingCapacity = new AtomicInteger(bearingCapacity.get());
        }

        boolean result = false;
        if (isLoading.get()) {
            try {
                if (vehicle.getArea() < freeArea.get() & vehicle.getWeight() < freeBearingCapacity.get()) {
                    int tempFreeArea = freeArea.get() - vehicle.getArea();
                    int tempFreeBearingCapacity = freeBearingCapacity.get() - vehicle.getWeight();
                    freeArea.getAndSet(tempFreeArea);
                    freeBearingCapacity.getAndSet(tempFreeBearingCapacity);
                    result = ferryQueue.offer(vehicle);
                    System.out.println(Thread.currentThread().getName() + " loaded");
                    int s = (int) ((area.get() * 0.1));
                    if (tempFreeArea < s) {
                        isLoading.getAndSet(false);
                    }
                } else {
                    isLoading.getAndSet(false);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return result;
    }

    public boolean runToUnload() {
        reentrantLock.lock();
        boolean result = false;
        try {
            if (!isLoading.get()) {
                ferryQueue.clear();
                isLoading.getAndSet(true);
                freeArea = new AtomicInteger(area.get());
                freeBearingCapacity = new AtomicInteger(bearingCapacity.get());
                result = true;
            }
        } finally {
            reentrantLock.unlock();
        }
        return result;
    }

}
