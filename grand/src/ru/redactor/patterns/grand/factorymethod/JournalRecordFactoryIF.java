package ru.redactor.patterns.grand.factorymethod;

import java.io.IOException;

public interface JournalRecordFactoryIF {

    JournalRecordIF nextRecord() throws IOException;

}
