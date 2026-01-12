package ru.redactor.patterns.grand.behavioral.strategy;

import java.util.Date;

public class CalendarDisplay {

    private HolidaySetIF holiday;

    /**
     * Экземпляры этого закрытого класса используются для кэширования информации,
     * связанной с датами, которые отображаются
     */
    private class DateCache {

        private Date date;
        private String[] holidayStrings;

        DateCache(Date dt) {
            date = dt;
            if (holiday == null) {
                holidayStrings = holiday.NO_HOLIDAY;
            } else {
                holidayStrings = holiday.getHolidays(date);
            }
        }

    }

}
