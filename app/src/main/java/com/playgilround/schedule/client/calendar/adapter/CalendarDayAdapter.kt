/*
package com.playgilround.schedule.client.calendar.adapter
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.annimon.stream.Stream.of
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.calendar.CalendarProperties
import com.playgilround.schedule.client.calendar.CalendarView
import com.playgilround.schedule.client.calendar.util.DateUtils
import com.playgilround.schedule.client.calendar.util.DayColorsUtils
import com.playgilround.schedule.client.calendar.util.SelectedDay
import java.util.*
import kotlin.collections.ArrayList

*/
/**
 * 19-08-12
 * 캘린더 하루 칸 로딩 Adapter
 *//*



class CalendarDayAdapter constructor(private val mCalendarPageAdapter: CalendarPageAdapter,
                                     mContext: Context,
                                     private val mCalendarProperties: CalendarProperties,
                                     dates: ArrayList<Date>,
                                     pageMonth: Int,
                                     private var mPosition: Int): ArrayAdapter<Date>(mContext, mCalendarProperties.itemLayoutResource, dates) {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val mToday = DateUtils.getCalendar()
    private var mPageMonth = 0

    init {
        mPageMonth = if (pageMonth < 0) 11 else pageMonth

    }
    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        var layoutView = view
        if (layoutView == null) layoutView = mLayoutInflater.inflate(mCalendarProperties.itemLayoutResource,
                parent, false)

        val tvDay = layoutView.findViewById<TextView>(R.id.dayLabel)
        val ivDay = layoutView.findViewById<ImageView>(R.id.dayIcon)

        val day = GregorianCalendar()
        day.time = getItem(position)

        if (ivDay != null) loadIcon(ivDay, day)

        setLabelColors(tvDay, day)

        tvDay.text = day.get(Calendar.DAY_OF_MONTH).toString()
        return layoutView
    }

    private fun setLabelColors(tvLabel: TextView, day: Calendar) {
        if (!isCurrentMonthDay(day)) DayColorsUtils.setDayColors(tvLabel, mCalendarProperties.anotherMonthsDaysLabelsColor,
                Typeface.NORMAL, R.drawable.background_transparent)

        if (isSelectedDay(day)) {
            of(mCalendarPageAdapter.getSelectedDays())
                    .filter { selectedDay -> selectedDay.calendar == day }
                    .findFirst().ifPresent { selectedDay -> selectedDay.view = tvLabel }

            if (day == mCalendarPageAdapter.getFirstSelectedDay())
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 1)
            else if (day == mCalendarPageAdapter.getLastSelectedDay())
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 2)
            else if (mCalendarPageAdapter.getFirstSelectedDay() == null || mCalendarPageAdapter.getLastSelectedDay() == null)
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 3)
            else DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 4)
        }
        DayColorsUtils.setCurrentMonthDayColor(day, mToday, tvLabel, mCalendarProperties)
    }

    private fun isSelectedDay(day: Calendar): Boolean {
        return mCalendarProperties.calendarType != CalendarView.CLASSIC &&
                day.get(Calendar.MONTH) == mPageMonth && mCalendarPageAdapter.getSelectedDays().contains(SelectedDay(day))
    }

    private fun isCurrentMonthDay(day: Calendar): Boolean {
        return day.get(Calendar.MONTH) == mPageMonth
    }

    private fun loadIcon(ivDay: ImageView, day: Calendar) {

    }

}
*/
