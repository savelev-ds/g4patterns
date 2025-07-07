package ru.redactor.patterns.grand.splitting.filter;

import java.io.IOException;

public interface InStreamIF {

    /**
     * Прочитать байты из байтового потока и записать их в массив
     * @param array - заполняемый массив
     * @return Если байтов недостаточно для заполнения массива, то этот метод завершается, заполнив массив реальным
     * количеством байтов. Возвращает общее количество байтов или -1, если достигнут конец потока данных.
     * @throws IOException Если появлется ошибка ввода/вывода
     */
    public int read(byte[] array) throws IOException;

}
