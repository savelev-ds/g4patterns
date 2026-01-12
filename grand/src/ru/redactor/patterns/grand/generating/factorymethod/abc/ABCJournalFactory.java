package ru.redactor.patterns.grand.generating.factorymethod.abc;

import ru.redactor.patterns.grand.generating.factorymethod.JournalRecordFactoryIF;
import ru.redactor.patterns.grand.generating.factorymethod.JournalRecordIF;
import ru.redactor.patterns.grand.generating.factorymethod.StartOfSale;

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
