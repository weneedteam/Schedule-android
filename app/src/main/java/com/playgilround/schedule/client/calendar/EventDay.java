package com.playgilround.schedule.client.calendar;

import android.graphics.drawable.Drawable;

import java.util.Calendar;

import androidx.annotation.DrawableRes;
import androidx.annotation.RestrictTo;

/**
 * 19-04-27
 * 스케줄 있을 경우 표시 관련 클래스
 */
public class EventDay {
    private Calendar mDay;
    private Object mDrawable;
    private boolean mIsDisabled;

    public EventDay(Calendar day) {
        mDay = day;
    }

    public EventDay(Calendar day, @DrawableRes int drawable) {
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
    }

    public EventDay(Calendar day, Drawable drawable) {
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Object getImageDrawable() {
        return mDrawable;
    }

    public Calendar getCalendar() {
        return mDay;
    }

    public boolean isEnabled() {
        return !mIsDisabled;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setEnabled(boolean enabled) {
        mIsDisabled = enabled;
    }
}
