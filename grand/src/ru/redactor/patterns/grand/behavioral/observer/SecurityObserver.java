package ru.redactor.patterns.grand.behavioral.observer;

public interface SecurityObserver {

    int ALARM = 1;
    int LOW_POWER = 2;
    int DIAGNOSTIC = 3;

    /**
     * Этот метод вызывается для передачи извещения от устройства безопасности
     * @param device - идентификатор устройства, от которого получено это извещение
     * @param event - одна из вышеупомянутых констант
     */
    void notify(int device, int event);

}
