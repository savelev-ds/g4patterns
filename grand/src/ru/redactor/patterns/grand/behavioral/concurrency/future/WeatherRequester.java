package ru.redactor.patterns.grand.behavioral.concurrency.future;

public class WeatherRequester {

    /**
     * Объект, который выполняет реальную работу по считыванию информации о погоде
     */
    WeatherFetchIF fetcher;

    public WeatherRequester(WeatherFetchIF fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Инициирует процесс получения текущих данных о погоде для места с заданными
     * географическими координатами.
     */
    public synchronized WeatherFuture getWeather(Coordinate location) {
        return new WeatherFuture(fetcher, location);
    }

}
