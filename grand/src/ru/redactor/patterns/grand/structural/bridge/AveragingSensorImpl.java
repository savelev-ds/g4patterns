package ru.redactor.patterns.grand.structural.bridge;

public interface AveragingSensorImpl extends SimpleSensorImpl {

    public void beginAverage() throws SensorException;

}
