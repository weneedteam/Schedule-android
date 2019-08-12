package com.playgilround.schedule.client.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.calendar.CalendarGridView
import com.playgilround.schedule.client.calendar.CalendarProperties
import com.playgilround.schedule.client.calendar.CalendarProperties.CALENDAR_SIZE
import com.playgilround.schedule.client.calendar.listener.DayRowClickListener
import com.playgilround.schedule.client.calendar.util.SelectedDay
import java.util.*

class CalendarPageAdapter(private val mContext: Context, private val mCalendarProperties: CalendarProperties) : PagerAdapter() {

    private lateinit var mCalendarGridView: CalendarGridView
    private var mPageMonth: Int = 0

    init {
        inforDatePicker()
    }

    override fun getCount(): Int {
        return CALENDAR_SIZE
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mCalendarGridView = inflater.inflate(R.layout.calendar_view_grid, null) as CalendarGridView

        loadMonth(position)

        mCalendarGridView.onItemClickListener = DayRowClickListener(this, mCalendarProperties, mPageMonth)
        container.addView(mCalendarGridView)

        return mCalendarGridView
    }

    //Add Day on GridView
    fun loadMonth(position: Int) {
        val days = ArrayList<Date>()

        val calendar = mCalendarProperties.firstPageDate.clone() as Calendar

        //오늘 날짜 얻기
        calendar.add(Calendar.MONTH, position)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        //Calendar 날짜가 무슨 요일?
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        //한 주의 시작이 무슨 요일?
        val firstDayOfWeek = calendar.firstDayOfWeek

        val monthBeginningCell = (if (dayOfWeek < firstDayOfWeek) 7 else 0) + dayOfWeek - firstDayOfWeek
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        while (days.size < 42) {
            days.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        mPageMonth = calendar.get(Calendar.MONTH) - 1
        val calendarDayAdapter = CalendarDayAdapter(this, mContext, mCalendarProperties, days, mPageMonth, position)

        mCalendarGridView.adapter = calendarDayAdapter
    }


    fun inforDatePicker() {
        if (mCalendarProperties.onSelectionAbilityListener != null)
            mCalendarProperties.onSelectionAbilityListener.onChange(mCalendarProperties.selectedDays.size > 0)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun getSelectedDays(): List<SelectedDay> {
        return mCalendarProperties.selectedDays
    }

    fun getSelectedDay(): SelectedDay {
        return mCalendarProperties.selectedDays[0]
    }

    fun setSelectedDay(selectedDay: SelectedDay) {
        mCalendarProperties.setSelectedDay(selectedDay)
        inforDatePicker()
    }

    fun addSelectedDay(selectedDay: SelectedDay) {
        if (!mCalendarProperties.selectedDays.contains(selectedDay)) {
            mCalendarProperties.selectedDays.add(selectedDay)
            inforDatePicker()
        }
    }

    fun getFirstSelectedDay(): Calendar? {
        val size = mCalendarProperties.selectedDays.size

        val firstCal = mCalendarProperties.selectedDays[0].calendar
        val secondCal = mCalendarProperties.selectedDays[size -1].calendar

        return if (size == 1) {
            null
        } else {
            if (firstCal.before(secondCal)) {
                firstCal
            } else {
                secondCal
            }
        }
    }

    fun getLastSelectedDay(): Calendar? {
        val size = mCalendarProperties.selectedDays.size
        val firstCal = mCalendarProperties.selectedDays[0].calendar
        val secondCal = mCalendarProperties.selectedDays[size -1].calendar

        return if (size == 1) {
            null
        } else {
            if (firstCal.before(secondCal)) {
                secondCal
            } else {
                firstCal
            }
        }
    }
}