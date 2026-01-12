package ru.redactor.patterns.grand.generating.abstractfactory.engolia;

import ru.redactor.patterns.grand.generating.abstractfactory.ArchitectureToolkitIF;
import ru.redactor.patterns.grand.generating.abstractfactory.CPU;
import ru.redactor.patterns.grand.generating.abstractfactory.MMU;

public class EngoliaToolkit implements ArchitectureToolkitIF {

    @Override
    public CPU createCPU() {
        return new EngoliaCPU();
    }

    @Override
    public MMU createMMU() {
        return new EngoliaMMU();
    }

}
