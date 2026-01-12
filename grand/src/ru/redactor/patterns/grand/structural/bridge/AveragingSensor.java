package ru.redactor.patterns.grand.structural.bridge;

public class AveragingSensor extends SimpleSensor {

    AveragingSensor(SimpleSensorImpl impl) {
        super(impl);
    }

    public void beginAverage() throws SensorException {
        ((AveragingSensorImpl) getImpl()).beginAverage();
    }
}
