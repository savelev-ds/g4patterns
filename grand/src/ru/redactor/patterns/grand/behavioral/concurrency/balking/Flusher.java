package ru.redactor.patterns.grand.behavioral.concurrency.balking;

public class Flusher {

    private boolean flushInProgress = false;

    /**
     * Этот метод вызывается для запуска водяной струи
     */
    public void flush() {
        synchronized (this) {
            if (flushInProgress) {
                return;
            }
            flushInProgress = true;
        }
        // Код для активизации водяной струи
    }

    /**
     * Этот метод вызывается с той целью, чтобы известить
     * данный объект о завершении действия водяной струи
     */
    public void flushCompleted() {
        flushInProgress = false;
    }

}
