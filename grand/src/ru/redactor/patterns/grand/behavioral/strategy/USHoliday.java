package ru.redactor.patterns.grand.behavioral.strategy;

import java.util.Date;

public class USHoliday  implements HolidaySetIF {

    @Override
    public String[] getHolidays(Date dt) {
        String[] holidays = HolidaySetIF.NO_HOLIDAY;
        // ...
        return holidays;
    }
}
