package ru.redactor.patterns.grand.behavioral.snapshot;

public class MilestoneMemento implements MilestoneMementoIF {

    private String description;

    MilestoneMemento(String description) {
        this.description = description;
    }

}
