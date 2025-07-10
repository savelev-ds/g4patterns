package ru.redactor.patterns.grand.structural.bridge;

public interface StreamingSensorImpl extends SimpleSensorImpl {

    public void setSamplingFreguency (int freq) throws SensorException ;

    public void setStreamingSensorListener (StreamingSensorListener listener) ;

}
