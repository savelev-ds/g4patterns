package ru.redactor.patterns.grand.behavioral.concurrency.scheduler;

public class Printer {

    private Scheduler scheduler = new Scheduler();

    public void print(JournalEntry j) {
        try {
            scheduler.enter(j);
            try {
                // ...
            } finally {
                scheduler.done();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
