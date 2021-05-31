package by.toxa.multithreading.entity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Vehicle extends Thread {
    private int vehicleId;
    private VehicleType vehicleType;

    public Vehicle(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        vehicleId = new Random().nextInt(100);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(vehicleType.toString() + ": ID = " + vehicleId);
        Ferry ferry = Ferry.getInstance();
        boolean load = false;
        while (!load) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            load = ferry.loadVehicle(this);
        }
    }

    public int getArea() {
        return vehicleType.getArea();
    }

    public int getWeight() {
        return vehicleType.getWeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (vehicleId != vehicle.vehicleId) return false;
        return vehicleType == vehicle.vehicleType;
    }

    @Override
    public int hashCode() {
        int result = vehicleId;
        result = 31 * result + (vehicleType != null ? vehicleType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID = ").append(vehicleId).append(" ");
        sb.append("Type = ").append(vehicleType).append(".");
        return sb.toString();
    }
}

