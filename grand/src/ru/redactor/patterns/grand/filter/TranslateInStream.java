package ru.redactor.patterns.grand.filter;

import java.io.IOException;

public class TranslateInStream extends FilterInStream {

    private byte[] translationTable;
    private final static int TRANS_TBL_LENGTH = 256;

    public TranslateInStream(InStreamIF inStream, byte[] table) throws IOException {
        super(inStream);
        translationTable = new byte[TRANS_TBL_LENGTH];
        System.arraycopy(table, 0, translationTable, 0, Math.min(TRANS_TBL_LENGTH, table.length));
        for (int i = table.length; i < TRANS_TBL_LENGTH; i++) {
            translationTable[i] = (byte) i;
        }
    }

    public int read(byte[] array) throws IOException {
        int count;
        count = super.read(array);
        for (int i = 0; i < count; i++) {
            array[i] = translationTable[array[i]];
        }
        return count;
    }

}
