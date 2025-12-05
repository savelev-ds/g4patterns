package ru.redactor.patterns.grand.behavioral.concurrency.future;

public class WeatherFuture {

    /**
     * Когда координата местности передается конструктору, он помещает эту координату, предназначенную для запроса,
     * в данную переменную экземпляра так, чтобы её видел новый запускаемый поток.
     */
    private Coordinate location;

    /**
     * Объект, используемый для считывания текущей информации о погоде
     */
    private WeatherFetchIF fetcher;

    /**
     * Объект, инкапсулирующий логику процесса получения информации
     */
    private AsynchronousFuture futureSupport;

    /**
     * Создаёт объект WeatherFuture, который инкапсулирует получение информации о погоде для данной координаты
     * местности, используя заданный объект WeatherFetchIF
     */
    public WeatherFuture(WeatherFetchIF fetcher, Coordinate location) {
        this.fetcher = fetcher;
        this.location = location;
        futureSupport = new AsynchronousFuture();
        new Runner().start();
    }

    /**
     * Возвращает true, если запрошенная информация о погоде была прочитана.
     */
    public boolean check() {
        return futureSupport.checkResult();
    }

    /**
     * Возвращает объект Result для этого объекта Future.
     * Если он еще не получен, ждёт, пока он не будет получен.
     */
    public synchronized WeatherIF waitForWeather() throws Exception {
        return (WeatherIF) futureSupport.getResult();
    }

    /**
     * Этот закрытый класс представляет общую логику для асинхронного считывания текущей информации о погоде
     */
    private class Runner extends Thread {
        public void run() {
            try {
                WeatherIF info = fetcher.fetchWeather(location);
                futureSupport.setResult(info);
            } catch (Exception e) {
                futureSupport.setException(e);
            }
        }
    }

}
