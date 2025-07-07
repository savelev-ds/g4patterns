package ru.redactor.patterns.grand.generating.abstractfactory;

import ru.redactor.patterns.grand.generating.abstractfactory.ember.EmberToolkit;
import ru.redactor.patterns.grand.generating.abstractfactory.engolia.EngoliaToolkit;

public class ToolkitFactory {

    private static ToolkitFactory myInstance = new ToolkitFactory();

    public static final int ENGOLIA = 900;
    public static final int EMBER = 901;

    public static ToolkitFactory getMyInstance() {
        return myInstance;
    }

    public ArchitectureToolkitIF createToolkit(int architecture) {
        switch (architecture) {
            case ENGOLIA: return new EngoliaToolkit();
            case EMBER: return new EmberToolkit();
            default: throw new IllegalArgumentException("Unknown arch " + architecture);
        }
    }
}
