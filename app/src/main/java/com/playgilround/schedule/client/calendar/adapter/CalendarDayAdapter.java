package com.playgilround.schedule.client.calendar.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendar.CalendarProperties;
import com.playgilround.schedule.client.calendar.CalendarView;
import com.playgilround.schedule.client.calendar.util.SelectedDay;
import com.playgilround.schedule.client.calendar.util.DateUtils;
import com.playgilround.schedule.client.calendar.util.DayColorsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;

/**
 * 캘린더 하루 칸 로딩 Adapter
 */



public class CalendarDayAdapter extends ArrayAdapter<Date> {

    private CalendarPageAdapter mCalendarPageAdapter;
    private LayoutInflater mLayoutInflater;
    private int mPageMonth;
    private int mPosition;
    private Calendar mToday = DateUtils.getCalendar();

    private CalendarProperties mCalendarProperties;
    private ArrayList<Calendar> selCalendar;

    private static final String TAG = CalendarDayAdapter.class.getSimpleName();


    CalendarDayAdapter(CalendarPageAdapter calendarPageAdapter, Context context, CalendarProperties calendarProperties,
                       ArrayList<Date> dates, int pageMonth, int position) {
        super(context, calendarProperties.getItemLayoutResource(), dates);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
        mPosition = position;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getItemLayoutResource(), parent, false);
        }

        TextView tvDay = view.findViewById(R.id.dayLabel);
        ImageView ivDay = view.findViewById(R.id.dayIcon);

        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));

        //Loading an Image of the event.
        if (ivDay != null) {
            loadIcon(ivDay, day);
        }

        setLabelColors(tvDay, day);

        tvDay.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
        return view;
    }

    private void setLabelColors(TextView tvLabel, Calendar day) {
        //Setting not current month day color

//        if (selCalendar == null) {
//            selCalendar = new ArrayList<>();
//            Log.d(TAG, "setLabel -> " + selCalendar.size());
//        }

        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(tvLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        // Set view for all SelectedDays
        if (isSelectedDay(day)) {

            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(tvLabel));

            if (day.equals(mCalendarPageAdapter.getFirstSelectedDay())) {
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 1);
            } else if (day.equals(mCalendarPageAdapter.getLastSelectedDay())) {
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 2);
            } else if (mCalendarPageAdapter.getFirstSelectedDay() == null || mCalendarPageAdapter.getLastSelectedDay() == null) {
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 3);
            } else {
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 4);
            }
            return;

        }
        DayColorsUtils.setCurrentMonthDayColor(day, mToday, tvLabel, mCalendarProperties);
    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth;
    }

    private void loadIcon(ImageView ivDay, Calendar day) {

    }
}
