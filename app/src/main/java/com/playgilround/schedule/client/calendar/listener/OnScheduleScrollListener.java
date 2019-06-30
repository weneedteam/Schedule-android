package com.playgilround.schedule.client.calendar.listener;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.playgilround.schedule.client.calendar.ScheduleLayout;

/**
 * 달력, 스케줄 부분 스크롤 리스너.
 */
public class OnScheduleScrollListener extends GestureDetector.SimpleOnGestureListener {

    private ScheduleLayout mScheduleLayout;
    private static final String TAG = OnScheduleScrollListener.class.getSimpleName();

    public OnScheduleScrollListener(ScheduleLayout scheduleLayout) {
        mScheduleLayout = scheduleLayout;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll...");
        mScheduleLayout.onCalendarScroll(distanceY);
        return true;
    }
}
