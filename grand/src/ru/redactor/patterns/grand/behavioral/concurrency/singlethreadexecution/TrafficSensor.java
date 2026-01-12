package ru.redactor.patterns.grand.behavioral.concurrency.singlethreadexecution;

public class TrafficSensor implements Runnable {

    private TrafficObserver observer;

    public TrafficSensor(TrafficObserver trafficObserver) {
        this.observer = trafficObserver;
        new Thread(this).start();
    }

    public void run() {
        monitorSensor();
    }

    private native void monitorSensor();

    private void detect() {
        observer.vehiclePassed();
    }

}
