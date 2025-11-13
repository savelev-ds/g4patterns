package ru.redactor.patterns.grand.behavioral.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecurityNotifier {

    private List<SecurityObserver> observers = Collections.synchronizedList(new ArrayList<>());

    public void addObserver(SecurityObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SecurityObserver observer) {
        observers.remove(observer);
    }

    private void notify(int device, int event) {
        observers.forEach(securityObserver -> securityObserver.notify(device, event));
    }

}
