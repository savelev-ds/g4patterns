package ru.redactor.patterns.grand.behavioral.observer;

public class SecurityAdapter implements SecurityObserver {

    private SecurityMonitor sm;

    SecurityAdapter(SecurityMonitor sm) {
        this.sm = sm;
    }

    public void notify(int device, int event) {
        switch (event) {
            case ALARM -> sm.securityAlert(device);
            case LOW_POWER, DIAGNOSTIC -> sm.diagnosticAlert(device);
        }
    }

}
