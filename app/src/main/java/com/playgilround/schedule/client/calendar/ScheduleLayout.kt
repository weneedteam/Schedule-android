package com.playgilround.schedule.client.calendar

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.calendar.util.ScheduleState
import io.realm.Realm.init
import kotlinx.android.synthetic.main.calendar_view.view.*
import kotlinx.android.synthetic.main.schedule_view.view.*

class ScheduleLayout @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    private val mAutoScrollDistance: Int = resources.getDimensionPixelOffset(R.dimen.auto_scroll_distance)
    private val mMinDistance: Int = resources.getDimensionPixelSize(R.dimen.calendar_min_distance)
    private val mRowSize: Int = resources.getDimensionPixelSize(R.dimen.week_calendar_height)
    private val mState = ScheduleState.OPEN

    init {

    }

    fun initAttrs(array: TypedArray) {

    }

    fun onCalendarScroll(distanceY: Float) {
        var distanceY = Math.min(distanceY, mAutoScrollDistance.toFloat())
        val calendarY: Float = calendarHeader.height.toFloat()

        llCalendarView.y = calendarY

        var scheduleY: Float = rlScheduleList.y - distanceY

        scheduleY = Math.min(scheduleY, llCalendarView.height.toFloat() - mRowSize)
        scheduleY = Math.max(scheduleY, calendarY)
        rlScheduleList.y = scheduleY
    }
}