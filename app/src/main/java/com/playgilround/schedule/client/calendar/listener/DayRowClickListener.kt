package com.playgilround.schedule.client.calendar.listener

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.annimon.stream.Stream.of
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.calendar.CalendarProperties
import com.playgilround.schedule.client.calendar.CalendarView
import com.playgilround.schedule.client.calendar.util.*
import com.playgilround.schedule.client.calendar.view.CalendarPageAdapter
import java.util.*

class DayRowClickListener(private var mCalendarPageAdapter: CalendarPageAdapter, private var mCalendarProperties: CalendarProperties, var pageMonth: Int) : AdapterView.OnItemClickListener {
    private val mPageMonth: Int = if (pageMonth < 0) 11 else pageMonth

    override fun onItemClick(adapterView: AdapterView<*>?, v: View, position: Int, id: Long) {
        val day = GregorianCalendar()
        day.time = adapterView!!.getItemAtPosition(position) as Date?

        if (mCalendarProperties.onDayClickListener != null) {
            onClick(day)
        }

        when (mCalendarProperties.calendarType) {
            CalendarView.RANGE_PICKER -> selectRange(v, day)
        }
    }

    private fun selectRange(v: View, day: Calendar) {
        val tvDay = v.findViewById<TextView>(R.id.dayLabel)

        if (!isCurrentMonthDay(day)) {
            return
        }

        val selectedDays = mCalendarPageAdapter.selectedDays

        if (selectedDays.size > 1) {
            clearAndSelectOne(tvDay, day)
        }

        if (selectedDays.size == 1) {
            selectOneAndRange(tvDay, day)
        }

        if (selectedDays.isEmpty()) {
            selectDay(tvDay, day)
        }
    }

    private fun clearAndSelectOne(tvDay: TextView, day: Calendar) {
        of(mCalendarPageAdapter.selectedDays).forEach(this::reverseUnselectedColor)
        selectDay(tvDay, day)
    }

    private fun selectOneAndRange(tvDay: TextView, day: Calendar) {
        val previousSelectedDay = mCalendarPageAdapter.selectedDay

        of(CalendarUtils.getDatesRange(previousSelectedDay.calendar, day))
                .forEach{ calendar -> mCalendarPageAdapter.addSelectedDay(SelectedDay(calendar))
        }

        DayColorsUtils.setSelectedDayColors(tvDay, mCalendarProperties, 4)

        mCalendarPageAdapter.addSelectedDay(SelectedDay(tvDay, day))
        mCalendarPageAdapter.notifyDataSetChanged()
    }


    fun onClick(day: Calendar) {
        if (mCalendarProperties.eventDays == null) {
            createEmptyEventDay(day)
            return
        }

        of(mCalendarProperties.eventDays)
                .filter{eventDate -> eventDate.calendar == day }
                .findFirst()
                .ifPresentOrElse(this::callOnClickListener) {createEmptyEventDay(day)}
    }

    private fun createEmptyEventDay(day: Calendar) {
        val eventDay = EventDay(day)
        callOnClickListener(eventDay)
    }

    private fun reverseUnselectedColor(selectedDay: SelectedDay) {
        DayColorsUtils.setCurrentMonthDayColor(selectedDay.calendar, DateUtils.getCalendar(), selectedDay.view as TextView, mCalendarProperties)
    }

    private fun selectDay(tvDay: TextView, day: Calendar) {
        DayColorsUtils.setSelectedDayColors(tvDay, mCalendarProperties, 3)
        mCalendarPageAdapter.selectedDay = SelectedDay(tvDay, day)
    }

    private fun callOnClickListener(eventDay: EventDay) {
        mCalendarProperties.onDayClickListener.onDayClick(eventDay)
    }

    private fun isCurrentMonthDay(day: Calendar): Boolean {
        return day.get(Calendar.MONTH) == mPageMonth && isBetweenMinAndMax(day)
    }

    private fun isBetweenMinAndMax(day: Calendar): Boolean {
        return !((mCalendarProperties.minimumDate != null &&
                    day.before(mCalendarProperties.minimumDate))
                || (mCalendarProperties.maximumDate != null && day.after(mCalendarProperties.maximumDate)))
    }
}