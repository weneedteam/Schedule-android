package com.playgilround.schedule.client.calendar.listener

import android.view.GestureDetector
import android.view.MotionEvent
import com.playgilround.schedule.client.calendar.ScheduleLayout

class OnScheduleScrollListener constructor(private val mScheduleLayout: ScheduleLayout): GestureDetector.SimpleOnGestureListener() {

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        mScheduleLayout.onCalendarScroll(distanceY)
        return true
    }
}