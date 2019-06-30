package com.playgilround.schedule.client.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 19-04-28
 */
public final class CalendarUtils {

    //두 날짜 사이 반환
    public static ArrayList<Calendar> getDatesRange(Calendar firstDay, Calendar lastDay) {
        if (lastDay.before(firstDay)) {
            //firstDay 가 더 이후일경우
            return getCalendarsBetweenDates(lastDay.getTime(), firstDay.getTime());
        }
        return getCalendarsBetweenDates(firstDay.getTime(), lastDay.getTime());
    }

    private static ArrayList<Calendar> getCalendarsBetweenDates(Date dateFrom, Date dateTo) {
        ArrayList<Calendar> calendars = new ArrayList<>();

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTime(dateFrom);

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(dateTo);

        long daysBetweenDates = TimeUnit.MILLISECONDS.toDays(
                calendarTo.getTimeInMillis() - calendarFrom.getTimeInMillis());

        for (int i = 1; i < daysBetweenDates; i++) {
            Calendar calendar = (Calendar) calendarFrom.clone();
            calendars.add(calendar);
            calendar.add(Calendar.DATE, i);
        }

        return calendars;
    }
}
