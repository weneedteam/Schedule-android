package com.playgilround.schedule.client.calendar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.GridView

class CalendarGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet?= null, defStyle: Int = 0): GridView(context, attrs, defStyle)  {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                MeasureSpec.AT_MOST)
        Log.d("TEST", "widthMeasure ---$widthMeasureSpec -- $expandSpec")
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
