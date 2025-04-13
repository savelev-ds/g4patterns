package ru.redactor.patterns.grand.abstractfactory.ember;

import ru.redactor.patterns.grand.abstractfactory.ArchitectureToolkitIF;
import ru.redactor.patterns.grand.abstractfactory.CPU;
import ru.redactor.patterns.grand.abstractfactory.MMU;

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
