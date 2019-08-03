package com.playgilround.schedule.client.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.calendar.listener.OnScheduleScrollListener
import com.playgilround.schedule.client.calendar.util.ScheduleState
import kotlinx.android.synthetic.main.calendar_view.view.*
import kotlinx.android.synthetic.main.schedule_view.view.*

class ScheduleLayout @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    private val mAutoScrollDistance: Int = resources.getDimensionPixelOffset(R.dimen.auto_scroll_distance)
    private val mMinDistance: Int = resources.getDimensionPixelSize(R.dimen.calendar_min_distance)
    private val mRowSize: Int = resources.getDimensionPixelSize(R.dimen.week_calendar_height)
    private var mState = ScheduleState.OPEN

    private val mDownPosition = FloatArray(2)
    private var mIsScrolling = false

    private lateinit var mGestureDetector: GestureDetector

    init {
        initGestureDetector()
    }

    private fun initGestureDetector() {
        mGestureDetector = GestureDetector(context, OnScheduleScrollListener(this))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        resetViewHeight(rlScheduleList, height - mRowSize)
        resetViewHeight(this, height)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.actionMasked) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_MOVE -> {
                transferEvent(e)
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                transferEvent(e)
                resetScrollingState()
                return true
            }
        }
        return super.onTouchEvent(e)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mDownPosition[0] = ev.rawX
                mDownPosition[1] = ev.rawY
                mGestureDetector.onTouchEvent(ev)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (mIsScrolling) {
            return true
        }

        when (ev?.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                val x = ev.rawX
                val y = ev.rawY

                val distanceX = Math.abs(x - mDownPosition[0])
                val distanceY = Math.abs(y - mDownPosition[1])

                if (distanceY > mMinDistance && distanceY > distanceX * 2.0f) {
                    return (y > mDownPosition[1] && isRecyclerViewTouch())
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
    private fun transferEvent(event: MotionEvent) {
        mGestureDetector.onTouchEvent(event)
    }

    private fun resetScrollingState() {
        mDownPosition[0] = 0F
        mDownPosition[1] = 0F
        mIsScrolling = false
    }




    fun onCalendarScroll(distanceY: Float) {
        val distanceY = Math.min(distanceY, mAutoScrollDistance.toFloat())
        val calendarY: Float = calendarHeader.height.toFloat()

        llCalendarView.y = calendarY

        var scheduleY: Float = rlScheduleList.y - distanceY

        scheduleY = Math.min(scheduleY, llCalendarView.height.toFloat() - mRowSize)
        scheduleY = Math.max(scheduleY, calendarY)
        rlScheduleList.y = scheduleY
    }

    private fun resetViewHeight(v: View, height: Int) {
        val layoutParams = v.layoutParams

        if (layoutParams.height != height) {
            layoutParams.height = height
            v.layoutParams = layoutParams
        }
    }

    private fun isRecyclerViewTouch() : Boolean {
        return mState == ScheduleState.CLOSE &&
                (rvScheduleList.childCount == 0 ||
                        rvScheduleList.isScrollTop)

    }
}