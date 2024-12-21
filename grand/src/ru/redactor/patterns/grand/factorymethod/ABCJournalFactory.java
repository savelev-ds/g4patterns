package ru.redactor.patterns.grand.factorymethod;

import java.io.DataInput;
import java.io.IOException;

public class ABCJournalFactory implements JournalRecordFactoryIF {

    private DataInput in;

    ABCJournalFactory(DataInput in) {
        this.in = in;
    }

    @Override
    public JournalRecordIF nextRecord() throws IOException {
        return new StartOfSale("ABC");
    }

}
