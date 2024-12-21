package ru.redactor.patterns.grand.factorymethod;

public class StartOfSale implements JournalRecordIF {

    private String txt;

    public StartOfSale(String txt) {
        this.txt = txt;
    }
}
