package ru.redactor.patterns.grand.structural.decorator;

public class AbstractDoorControllerWrapper implements DoorControllerIF {

    private DoorControllerIF wrapee;

    AbstractDoorControllerWrapper (DoorControllerIF wrapee) {
        this.wrapee = wrapee;
    }

    @Override
    public void requestOpen(String key) {
        wrapee.requestOpen(key);
    }

    @Override
    public void close(String key) {
        wrapee.close(key);
    }

}
