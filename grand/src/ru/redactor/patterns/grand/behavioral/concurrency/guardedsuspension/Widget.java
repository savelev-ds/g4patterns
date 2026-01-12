package ru.redactor.patterns.grand.behavioral.concurrency.guardedsuspension;

public class Widget {

    synchronized void foo() throws InterruptedException {
        while (!isOk()) {
            wait();
        }
    }

    synchronized void bar(int x) {
        notify();
    }

    private boolean isOk() {
        return true;
    }

}
