package ru.redactor.patterns.grand.factorymethod.abc;

import ru.redactor.patterns.grand.factorymethod.JournalRecordFactoryIF;
import ru.redactor.patterns.grand.factorymethod.JournalRecordIF;
import ru.redactor.patterns.grand.factorymethod.StartOfSale;

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
