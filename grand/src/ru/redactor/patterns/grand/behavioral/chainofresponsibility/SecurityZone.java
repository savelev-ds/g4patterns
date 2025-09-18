package ru.redactor.patterns.grand.behavioral.chainofresponsibility;

public abstract class SecurityZone implements SecurityZoneIF {

    private SecurityZone parent;

    /**
     * Возвращает родительскую зону этого объекта
     * @return
     */
    SecurityZone getParent() {
        return parent;
    }

    /**
     * Вызываем этот метод для оповещения зоны о новом измеренном значении сенсора
     */
    public void notify(int measurement, Sensor sensor) {
        if (!handleNotification(measurement, sensor) && parent != null) {
            parent.notify(measurement, sensor);
        }
    }

    /**
     * Этот метод вызывается методом notify, поэтому объект может обработать измерения.
     */
    protected abstract boolean handleNotification(int measurement, Sensor sensor);

    /**
     * Этот метод вызывается дочерней зоной для сообщения о пожаре.
     * Предполагается, что зона потомка должна включить разбрызгиватели или предпринять другие меры по контролю
     * над огнём в пределах своей зоны. Цель этого метода состоит в том, чтобы он был замещен в подклассах и мог
     * предпринимать любые необходимые меры за пределами зоны потомка
     */
    @Override
    public void fireAlarm(SecurityZone zone) {
        if (parent != null) {
            parent.fireAlarm(zone);
        }
    }
}
