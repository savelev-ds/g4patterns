package ru.redactor.patterns.grand.splitting.filter;

import java.io.IOException;

public abstract class FilterInStream implements InStreamIF {

    private InStreamIF inStream;

    /**
     * @param inStream - объект, которому данный объект должен делегировать операции чтения
     * @throws IOException
     */
    public FilterInStream(InStreamIF inStream) throws IOException {
        this.inStream = inStream;
    }

    public int read(byte[] array) throws IOException {
        return inStream.read(array);
    }

}
