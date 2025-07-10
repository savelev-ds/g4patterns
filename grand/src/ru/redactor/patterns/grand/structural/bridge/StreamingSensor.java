package ru.redactor.patterns.grand.structural.bridge;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public class StreamingSensor extends SimpleSensor implements StreamingSensorListener, Runnable {

    /**
     * Эти объекты предоставляют буфер, позволяющий объекту реализации асинхронно передавать измеренные
     * значения в то время, когда этот объект передает уже полученное значение своим приёмникам
     */
    private DataInputStream consumer;
    private DataOutputStream producer;

    // Коллекция приёмников
    private Vector listeners = new Vector();

    StreamingSensor(StreamingSensorImpl impl) throws SensorException {
        super(impl);

        // Создаёт канальный поток, который будет поддерживать способность этого объекта передавать измеренные
        // данные одновременно с их получением
        PipedInputStream pipedInputStream = new PipedInputStream();
        consumer = new DataInputStream(pipedInputStream);
        PipedOutputStream pipedOutput;
        try {
            pipedOutput = new PipedOutputStream(pipedInputStream);
        } catch (IOException exc) {
            throw new SensorException("pipe creation failed");
        }
        producer = new DataOutputStream(pipedOutput);

        new Thread(this).start();
    }

    /**
     * Потоковые сенсоры выдают поток измеренных величин. Частота выдачи потока значений не превышает заданное
     * количество раз в минуту
     * @param freq - максимальная частота (раз в минуту) выдачи результатов измерений данным потоковым сенсором
     */
    public void setSamplingFrequency(int freq) throws SensorException {
        StreamingSensorImpl impl;
        impl = (StreamingSensorImpl) getImpl();
        impl.setSamplingFrequency(freq);
    }

    /**
     * Объекты StreamingSensor предоставляют поток значений заинтересованным в этом объектам, передавая каждое
     * значение методу processMeasurement объекта. Передача значений осуществляется при помощи собственного потока
     * и является полностью асинхронной.
     * @param value -  Передаваемое измеренное значение
     */
    public void processMeasurement(int value) {
        try {
            producer.writeInt(value);
        } catch (IOException exc) {
            // Не может передать значение, просто отбрасываем его
        }
    }

    public void addStreamingSensorListener(StreamingSensorListener listener) {
        listeners.addElement(listener);
    }

    public void removeStreamingSensorListener(StreamingSensorListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Этот метод асинхронно удаляет измеренные значения из канала и передает их зарегестрированным получателям
     */
    public void run() {
        while (true) {
            int value;
            try {
                value = consumer.read();
            } catch (IOException e) {
                // канал не работает, поэтому выводим из метода,
                // что приводит в окончанию выполнения потока
                return;
            }
            for (int i = 0; i < listeners.size(); i++) {
                StreamingSensorListener listener;
                listener = (StreamingSensorListener) listeners.elementAt(i);
                listener.processMeasurement(value);
            }
        }
    }

}
