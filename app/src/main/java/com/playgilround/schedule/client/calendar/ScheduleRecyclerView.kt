package com.playgilround.schedule.client.calendar

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * 19-04-15
 * 해당날짜에 스케줄 정보 표시 RecyclerView
 * https://www.charlezz.com/?p=103 JvmOverloads 추가
 */
class ScheduleRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    //스크롤이 탑입니다.
    val isScrollTop: Boolean
        get() = computeVerticalScrollOffset() == 0

    override fun requestChildFocus(child: View, focused: View) {
        super.requestChildFocus(child, focused)
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(child, false)
            onFocusChangeListener.onFocusChange(focused, true)
        }
    }

}
