package ru.redactor.patterns.grand.behavioral.chainofresponsibility;

public class Area extends SecurityZone {

    /**
     * Этот метод вызывается методом notify,
     * поэтому объект может обработать измерения
     */
    protected boolean handleNotification(int measurement, Sensor sensor) {
        if (sensor instanceof TemperatureSensor) {
            if (measurement > 150) {
                fireAlarm(this);
                return true;
            }
        }
        // ...
        return false;
    }

}
