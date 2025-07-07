package ru.redactor.patterns.grand.splitting.filter;

import java.io.IOException;

public class ByteCountInStream extends FilterInStream {

    private long byteCount = 0;

    public ByteCountInStream(InStreamIF inStream) throws IOException {
        super(inStream);
    }

    public int read(byte[] array) throws IOException {
        int count;
        count = super.read(array);
        if (count > 0) {
            byteCount += count;
        }
        return count;
    }

    public long getByteCount() {
        return byteCount;
    }

}
