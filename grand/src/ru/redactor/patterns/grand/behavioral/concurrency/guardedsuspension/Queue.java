package ru.redactor.patterns.grand.behavioral.concurrency.guardedsuspension;

import java.util.ArrayList;

public class Queue {

    private ArrayList data = new ArrayList();

    synchronized public void put(Object o) {
        data.add(o);
        notify();
    }

    synchronized public Object get() {
        while (data.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        Object obj = data.get(0);
        data.remove(0);
        return obj;
    }

}
