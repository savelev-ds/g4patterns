package ru.redactor.patterns.grand.behavioral.chainofresponsibility;

public interface SecurityZoneIF {

    /**
     * Этот метод вызывается для оповещения зоны контроля безопасности об изменении значения сенсора
     * @param measurement
     * @param source
     */
    void notify(int measurement, Sensor source);

    /**
     * Этот метод вызывается дочерней зоной для сообщения о пожаре
     */
    void fireAlarm(SecurityZone zone);

}
