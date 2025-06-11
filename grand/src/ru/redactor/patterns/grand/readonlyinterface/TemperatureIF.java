package ru.redactor.patterns.grand.readonlyinterface;

public interface TemperatureIF {

    public double getTemperature();

    public void addListener(TemperatureIF listener);

    public void removeListener(TemperatureIF listener);

}
