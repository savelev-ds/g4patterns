package ru.redactor.patterns.grand.behavioral.concurrency.singlethreadexecution;

public class TrafficTransmitter implements Runnable {

    private TrafficSensorController controller;
    private Thread myThread;

    public TrafficTransmitter(TrafficSensorController controller) {
        this.controller = controller;
        // ...
        myThread = new Thread(this);
        myThread.start();
    }

    public void run() {
        while (true) {
            try {
                myThread.sleep(60 * 1000);
                transmit(controller.getAndClearCount());
            } catch (InterruptedException exc) {

            }
        }
    }

    private native void transmit(int count);

}
