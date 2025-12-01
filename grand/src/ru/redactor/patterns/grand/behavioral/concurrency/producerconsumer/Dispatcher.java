package ru.redactor.patterns.grand.behavioral.concurrency.producerconsumer;

public class Dispatcher implements Runnable {

    private Queue myQueue;

    public Dispatcher(Queue queue) {
        this.myQueue = queue;
    }

    public void run() {
        TroubleTicket tht = myQueue.pull();
    }

}
