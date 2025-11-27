package ru.redactor.patterns.grand.behavioral.concurrency.scheduler;

public interface ScheduleOrdering {

    public boolean scheduleBefore(ScheduleOrdering s);

}
