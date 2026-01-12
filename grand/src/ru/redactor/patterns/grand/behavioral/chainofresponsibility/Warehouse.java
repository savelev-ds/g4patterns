package ru.redactor.patterns.grand.behavioral.chainofresponsibility;

public class Warehouse extends SecurityZone {

    /**
     *  Этот метод вызывается методом notify,
     *  поэтому объект может обработать измерения
     */
    @Override
    protected boolean handleNotification(int measurement, Sensor sensor) {
        // ...
        return false;
    }

    public void fireAlarm(SecurityZone zone) {
        if (zone instanceof Area) {
            // Включаем разбрызгиватели в близлежащих областях
            // ...
            // Не вызываем super.fireAlarm, так как при этом включаются разбрызгиватели по всему складу
            if (getParent() != null) {
                getParent().fireAlarm(zone);
            }
            return;
        }
        super.fireAlarm(zone);
    }

}
