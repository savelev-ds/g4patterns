package ru.redactor.patterns.grand.abstractfactory;

public class Client {

    public void work() {

        ArchitectureToolkitIF toolkit = ToolkitFactory.getMyInstance().createToolkit(ToolkitFactory.EMBER);
        CPU cpu = toolkit.createCPU();
        MMU mmu = toolkit.createMMU();

        // use CPU, use MMU

    }

}
