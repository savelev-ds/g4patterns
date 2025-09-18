package ru.redactor.patterns.grand.behavioral.chainofresponsibility;

public class TemperatureSensor extends Sensor {

    private SecurityZone zone;

    /**
     * Если датчик температуры, связанный с объектом, фиксирует изменение температуры,
     * вызывается метод notify
     */
    public void notify(int measurement) {
        zone.notify(measurement, this);
    }


}
