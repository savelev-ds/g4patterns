package ru.redactor.patterns.grand.structural.dynamiclinkage;

public class ConcreteFoodProcessorProgram extends AbstractFoodProcessorProgram {

    @Override
    public String getName() {
        return "Chocolate Milk";
    }

    @Override
    public void start() {
        double weight = getEnvironment().weight();
        if (weight > 120.0 &&  weight < 160.0) {
            getEnvironment().mix(4);
        }
    }
}
