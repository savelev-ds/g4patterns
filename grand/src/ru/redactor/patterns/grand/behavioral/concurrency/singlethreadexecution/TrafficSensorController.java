package ru.redactor.patterns.grand.behavioral.concurrency.singlethreadexecution;

public class TrafficSensorController implements TrafficObserver {

    private int vehicleCount = 0;

    public synchronized void vehiclePassed() {
        vehicleCount++;
    }

    public synchronized int getAndClearCount() {
        int count = vehicleCount;
        vehicleCount = 0;
        return count;
    }

}
