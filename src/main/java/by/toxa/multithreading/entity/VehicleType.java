package by.toxa.multithreading.entity;

public enum VehicleType {
    CAR(7, 2000),
    TRUCK(19, 20000);

    private int area;
    private int weight;

    VehicleType(int area, int weight) {
        this.area = area;
        this.weight = weight;
    }

    public int getArea() {
        return area;
    }

    public int getWeight() {
        return weight;
    }
}
