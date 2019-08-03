package com.playgilround.schedule.client.calendar.util;

import android.content.Context;


import com.playgilround.schedule.client.R;

import org.joda.time.DateTime;

import java.util.Calendar;

public class DateUtils {

    public static DateTime getDateTime() {
        return new DateTime();
    }

    public static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public static boolean isMonthBefore(Calendar first, Calendar second) {
        if (first == null) {
            return false;
        }

        Calendar firstDay = (Calendar) first.clone();
        setMidnight(firstDay);
        firstDay.set(Calendar.DAY_OF_MONTH, 1);

        Calendar secondDay = (Calendar) second.clone();
        setMidnight(secondDay);
        secondDay.set(Calendar.DAY_OF_MONTH, 1);

        return secondDay.before(firstDay);
    }

    public static boolean isMonthAfter(Calendar first, Calendar second) {
        if (first == null) {
            return false;
        }

        Calendar firstDay = (Calendar) first.clone();
        setMidnight(firstDay);
        firstDay.set(Calendar.DAY_OF_MONTH, 1);

        Calendar secondDay = (Calendar) second.clone();
        setMidnight(secondDay);
        secondDay.set(Calendar.DAY_OF_MONTH, 1);

        return secondDay.after(firstDay);
    }

    /**
     * This method sets an hour in the calendar object to 00:00:00:00
     *
     * @param calendar Calendar object which hour should be set to 00:00:00:00
     */
    public static void setMidnight(Calendar calendar) {
        if (calendar != null) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
    }
    //월 이름, 연도 포함 문자 반환.
    public static String getMonthAndYearDate(Context context, Calendar calendar) {
        return String.format("%s  %s", context.getResources().getStringArray(R.array.material_calendar_months_array)
                [calendar.get(Calendar.MONTH)], calendar.get(Calendar.YEAR));
    }

}
