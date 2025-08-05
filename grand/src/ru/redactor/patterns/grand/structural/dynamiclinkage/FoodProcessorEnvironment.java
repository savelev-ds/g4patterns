package ru.redactor.patterns.grand.structural.dynamiclinkage;

import java.net.URL;
import java.net.URLClassLoader;

public class FoodProcessorEnvironment implements FoodProcessorEnvironmentIF {

    private static final URL[] classPath;

    static {
        try {
            classPath = new URL[]{ new URL("file:///bin") };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void slice(int width) {

    }

    @Override
    public void mix(int speed) {

    }

    @Override
    public double weight() {
        return 0;
    }

    void run(String programName) {
        URLClassLoader classLoader = new URLClassLoader(classPath);
        Class programClass;
        try {
            programClass = classLoader.loadClass(programName);
        } catch (Exception e) {
            return;
        }
        AbstractFoodProcessorProgram program;
        try {
            program = (AbstractFoodProcessorProgram) programClass.newInstance();
        } catch (Exception e) {
            return;
        }
        program.setEnvironment(this);
        System.out.println(program.getName());
        program.start();
    }

}
