package ru.redactor.patterns.grand.behavioral.concurrency.producerconsumer;

import java.util.ArrayList;

public class Queue {

    private ArrayList data = new ArrayList();

    /**
     * Помещаем объект в конец очереди.
     */
    synchronized public void push(TroubleTicket tkt) {
        data.add(tkt);
        notify();
    }

    /**
     * Получаем TroubleTicket, находящицся в начале очереди.
     * Если очередь пуста, ждём, пока в ней не появится объект.
     */
    synchronized public TroubleTicket pull() {
        while (data.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        TroubleTicket tkt = (TroubleTicket) data.get(0);
        data.remove(0);
        return tkt;
    }

    public int size() {
        return data.size();
    }


}
