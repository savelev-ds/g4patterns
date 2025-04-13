package ru.redactor.patterns.grand.abstractfactory.engolia;

import ru.redactor.patterns.grand.abstractfactory.ArchitectureToolkitIF;
import ru.redactor.patterns.grand.abstractfactory.CPU;
import ru.redactor.patterns.grand.abstractfactory.MMU;

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
