package ru.redactor.patterns.grand.behavioral.strategy;

import java.util.Date;

public interface HolidaySetIF {

    String[] NO_HOLIDAY = new String[0];

    /**
     * Возвращает массив строк, описывающих праздники, которые выпадают на определённые даты.
     * Если на эту дату не выпадает никаких праздников, возвращает NO_HOLIDAY
     */
    public String[] getHolidays(Date dt);

}
