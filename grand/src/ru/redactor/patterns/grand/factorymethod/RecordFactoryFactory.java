package ru.redactor.patterns.grand.factorymethod;

import ru.redactor.patterns.grand.factorymethod.abc.ABCJournalFactory;
import ru.redactor.patterns.grand.factorymethod.xyz.XYZJournalFactory;

import java.io.DataInput;
import java.lang.reflect.Constructor;

/**
 *  Необходимо, чтобы ни источник данных, ни источник события, ни клиенты
 *  не были осведомлены о реальном типе создаваемого объекта.
 */
public class RecordFactoryFactory {

    private Constructor factoryConstructor;

    public static final String ABC = "ABC";
    public static final String XYZ = "XYZ";

    public RecordFactoryFactory(String posType) {

        Class[] params = { DataInput.class };
        Class factoryClass = switch (posType) {
            case ABC -> ABCJournalFactory.class;
            case XYZ -> XYZJournalFactory.class;
            default -> throw new RuntimeException("Unknown pos type " + posType);
        };

        try {
            factoryConstructor = factoryClass.getConstructor(params);
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }

    }

    public JournalRecordFactoryIF createFactory(DataInput in) {

        Object[] args = { in };
        Object factory;

        try {
            factory = factoryConstructor.newInstance(args);
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }

        return (JournalRecordFactoryIF) factory;

    }

}
