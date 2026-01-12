package ru.redactor.patterns.grand.structural.dynamiclinkage;

public abstract class AbstractFoodProcessorProgram {

    private FoodProcessorEnvironmentIF environmentIF;

    public void setEnvironment(FoodProcessorEnvironmentIF environmentIF) {
        this.environmentIF = environmentIF;
    }

    public FoodProcessorEnvironmentIF getEnvironment() {
        return environmentIF;
    }

    public abstract String getName();

    public abstract void start();

}
