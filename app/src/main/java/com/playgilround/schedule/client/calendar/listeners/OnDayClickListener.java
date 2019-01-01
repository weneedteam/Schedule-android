package com.playgilround.schedule.client.calendar.listeners;


import com.playgilround.schedule.client.calendar.EventDay;

/**
 * This interface is used to handle clicks on calendar cells
 * <p>
 * Created by Mateusz Kornakiewicz on 23.05.2017.
 */

public interface OnDayClickListener {
    void onDayClick(EventDay eventDay);
}
