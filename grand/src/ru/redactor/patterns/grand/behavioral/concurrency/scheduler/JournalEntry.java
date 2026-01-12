package ru.redactor.patterns.grand.behavioral.concurrency.scheduler;

import java.util.Date;

public class JournalEntry implements ScheduleOrdering {

    private Date time = new Date();

    public Date getTime() {
        return time;
    }

    @Override
    public boolean scheduleBefore(ScheduleOrdering s) {
        if (s instanceof JournalEntry je) {
            return getTime().before(je.getTime());
        }
        return false;
    }

}
