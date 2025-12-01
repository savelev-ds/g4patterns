package ru.redactor.patterns.grand.behavioral.concurrency.producerconsumer;

public class Client implements Runnable {

    private Queue myQueue;

    public Client(Queue queue) {
        this.myQueue = queue;
    }

    public void run() {
        TroubleTicket tkt = null;
        // ...
        myQueue.push(tkt);
    }

}
