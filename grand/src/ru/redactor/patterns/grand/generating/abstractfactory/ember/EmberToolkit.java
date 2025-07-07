package ru.redactor.patterns.grand.generating.abstractfactory.ember;

import ru.redactor.patterns.grand.generating.abstractfactory.ArchitectureToolkitIF;
import ru.redactor.patterns.grand.generating.abstractfactory.CPU;
import ru.redactor.patterns.grand.generating.abstractfactory.MMU;

public class EmberToolkit implements ArchitectureToolkitIF {

    @Override
    public CPU createCPU() {
        return new EmberCPU();
    }

    @Override
    public MMU createMMU() {
        return new EmberMMU();
    }

}
