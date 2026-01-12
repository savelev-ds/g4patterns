package ru.redactor.patterns.grand.splitting.filter;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileInStream implements InStreamIF {

    private RandomAccessFile file;

    public FileInStream(String fName) throws IOException {
        file = new RandomAccessFile(fName, "r");
    }

    /**
     * Считывает байты из файла и заполняет этими байтами массив
     * @param array - заполняемый массив
     * @return
     * @throws IOException
     */
    public int read(byte[] array) throws IOException {
        return file.read(array);
    }

}
