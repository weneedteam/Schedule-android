package com.playgilround.schedule.client.calendar.listener

import com.playgilround.schedule.client.calendar.util.EventDay

interface OnDayClickListener {
    fun onDayClick(eventDay: EventDay)
}