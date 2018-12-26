package com.playgilround.schedule.client.Calendar;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.RestrictTo;


import com.playgilround.schedule.client.Calendar.utils.DateUtils;

import java.util.Calendar;

/**
 * This class represents an event of a day. An instance of this class is returned when user click
 * a day cell. This class can be overridden to make calendar more functional. A list of instances of
 * this class can be passed to CalendarView object using setEvents() method.
 * <p>
 * Created by Mateusz Kornakiewicz on 23.05.2017.
 */

public class EventDay {
    private Calendar mDay;
    private Object mDrawable;
    private Object Strings;
    private boolean mIsDisabled;

    /**
     * @param day Calendar object which represents a date of the event
     */
    public EventDay(Calendar day) {
        mDay = day;
    }

    /**
     * @param day      Calendar object which represents a date of the event
     * @param drawable Drawable resource which will be displayed in a day cell
     */
    public EventDay(Calendar day, @DrawableRes int drawable) {
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
    }

    /**
     * @param day      Calendar object which represents a date of the event
     * @param drawable Drawable which will be displayed in a day cell
     */
    public EventDay(Calendar day, Drawable drawable) {
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
    }

    public EventDay(Calendar day, String in, String out) {
        DateUtils.setMidnight(day);
        mDay = day;
        Strings = in+","+out;
    }
    /**
     * @return An image resource which will be displayed in the day row
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Object getTextData() {
        return Strings;
    }


    /**
     * @return Calendar object which represents a date of current event
     */
    public Calendar getCalendar() {
        return mDay;
    }


    /**
     * @return Boolean value if day is not disabled
     */
    public boolean isEnabled() {
        return !mIsDisabled;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setEnabled(boolean enabled) {
        mIsDisabled = enabled;
    }
}
