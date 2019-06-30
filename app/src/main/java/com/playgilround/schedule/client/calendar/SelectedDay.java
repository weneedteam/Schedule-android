package com.playgilround.schedule.client.calendar;

import android.view.View;

import java.util.Calendar;

/**
 * 19-04-27
 * 선택된 날짜 모음
 */
public class SelectedDay {
    private View mView;
    private Calendar mCalendar;

    public SelectedDay(Calendar calendar) {
        mCalendar = calendar;
    }

    public SelectedDay(View v, Calendar calendar) {
        mView = v;
        mCalendar = calendar;
    }

    public View getView() {
        return mView;
    }

    public void setView(View v) {
        mView = v;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SelectedDay) {
            return getCalendar().equals(((SelectedDay) obj).getCalendar());
        }

        if (obj instanceof Calendar)
            return getCalendar().equals(obj);

        return super.equals(obj);
    }

}
