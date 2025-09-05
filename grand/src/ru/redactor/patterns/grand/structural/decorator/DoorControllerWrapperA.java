package ru.redactor.patterns.grand.structural.decorator;

public class DoorControllerWrapperA extends AbstractDoorControllerWrapper {

    private String camera;

    private SurveillanceMonitorIF monitor;

    public DoorControllerWrapperA(DoorControllerIF wrapee, String camera, SurveillanceMonitorIF monitor) {
        super(wrapee);
        this.camera = camera;
        this.monitor = monitor;
    }

    @Override
    public void requestOpen(String key) {
        monitor.viewNow(camera);
        super.requestOpen(key);
    }

}
