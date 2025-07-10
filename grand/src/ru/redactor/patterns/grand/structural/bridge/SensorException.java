package ru.redactor.patterns.grand.structural.bridge;

public class SensorException extends Exception {

    public SensorException(String pipeCreationFailed) {
        super(pipeCreationFailed);
    }
}
