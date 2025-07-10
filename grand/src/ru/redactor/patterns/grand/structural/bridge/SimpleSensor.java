package ru.redactor.patterns.grand.structural.bridge;

public class SimpleSensor {

    /**
     * Ссылка на объект, который реализует операции,
     * специфические для реального сенсорного устройства,
     * представленного этим объектом
     */
    private SimpleSensorImpl impl;

    SimpleSensor(SimpleSensorImpl impl) {
        this.impl = impl;
    }

    protected SimpleSensorImpl getImpl() {
        return impl;
    }

    public int getValue() throws SensorException {
        return impl.getValue();
    }

}
