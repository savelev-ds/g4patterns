package ru.redactor.patterns.grand.behavioral.concurrency.lockobject;

public abstract class AbstractGameObject {

    private static final Object lockObject = new Object();

    private boolean glowing;

    public static final Object getLockObject() {
        return lockObject;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

}
