package ru.redactor.patterns.grand.behavioral.concurrency.future;

public interface WeatherFetchIF {

    WeatherIF fetchWeather(Coordinate location);

}
