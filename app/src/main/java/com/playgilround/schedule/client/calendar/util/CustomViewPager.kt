package com.playgilround.schedule.client.calendar.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import io.realm.Realm.init

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var enable: Boolean = true

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return enable and super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return enable and super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled() {
        enable = false
    }

}