/*
package com.playgilround.schedule.client.calendar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class CalendarViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null): ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasure: Int = heightMeasureSpec
        var height = 0

        Log.d("TEST", "onMeasure --->$childCount")
        for (i in 0..childCount) {
            val child = getChildAt(i)

            Log.d("TEST", "child state-> $child -- $widthMeasureSpec")
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))

            val h = child.measuredHeight

            if (h > height) height = h
        }

        if (height != 0) heightMeasure = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)

        Log.d("TEST", "widthMeasure ---$widthMeasureSpec -- $heightMeasure")

        super.onMeasure(widthMeasureSpec, heightMeasure)
    }
}
*/
