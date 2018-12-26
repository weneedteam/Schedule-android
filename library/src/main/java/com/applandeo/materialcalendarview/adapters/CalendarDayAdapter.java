package com.applandeo.materialcalendarview.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.applandeo.materialcalendarview.utils.DayColorsUtils;
import com.applandeo.materialcalendarview.utils.ImageUtils;
import com.applandeo.materialcalendarview.utils.SelectedDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class is responsible for loading a one day cell.
 * <p>
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */

class CalendarDayAdapter extends ArrayAdapter<Date> {
    private CalendarPageAdapter mCalendarPageAdapter;
    private LayoutInflater mLayoutInflater;
    private int mPageMonth;
    private Calendar mToday = DateUtils.getCalendar();

    private CalendarProperties mCalendarProperties;

    CalendarDayAdapter(CalendarPageAdapter calendarPageAdapter, Context context, CalendarProperties calendarProperties,
                       ArrayList<Date> dates, int pageMonth) {
        super(context, calendarProperties.getItemLayoutResource(), dates);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getItemLayoutResource(), parent, false);
        }

        TextView dayLabel = (TextView) view.findViewById(R.id.dayLabel);
        TextView dayIn = (TextView) view.findViewById(R.id.dayin);
        TextView dayOut = (TextView) view.findViewById(R.id.dayout);

        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));

        // Loading an image of the event
        if (dayIn != null && dayOut != null) {
            loadIcon(dayIn, dayOut, day);
        }

        setLabelColors(dayLabel, day);

        dayLabel.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
        return view;
    }

    private void setLabelColors(TextView dayLabel, Calendar day) {

        // Setting not current month day color
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        // Set view for all SelectedDays
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(dayLabel));

            DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
            return;
        }

        // Setting disabled days color
        if (!isActiveDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getDisabledDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        //일요일
        if (day.get(Calendar.DAY_OF_WEEK)==1) {
            DayColorsUtils.setDayColorsRED(dayLabel);
            return;
        }

        //토요일
        if (day.get(Calendar.DAY_OF_WEEK)==7) {
            DayColorsUtils.setDayColorsBLUE(dayLabel);
            return;
        }

        // Setting current month day color
        DayColorsUtils.setCurrentMonthDayColors(day, mToday, dayLabel, mCalendarProperties);
    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth &&
                !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                        || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private boolean isActiveDay(Calendar day) {
        return !mCalendarProperties.getDisabledDays().contains(day);
    }

    private void loadIcon(TextView dayIn, TextView dayOut, Calendar day) {
        if (mCalendarProperties.getEventDays() == null || !mCalendarProperties.getEventsEnabled()) {
            dayIn.setVisibility(View.GONE);
            dayOut.setVisibility(View.GONE);
            return;
        }

        Stream.of(mCalendarProperties.getEventDays()).filter(eventDate ->
                eventDate.getCalendar().equals(day)).findFirst().executeIfPresent(eventDay -> {
            String[] arr = ((String) eventDay.getTextData()).split(",");
            if ((!arr[0].equals("null")) && (!arr[0].equals(""))) dayIn.setText(arr[0]);
            if ((!arr[1].equals("null")) && (!arr[1].equals(""))) dayOut.setText(arr[1]);
            //ImageUtils.loadImage(dayTEXT, eventDay.getTextData());

            // If a day doesn't belong to current month then image is transparent
            if (!isCurrentMonthDay(day) || !isActiveDay(day)) {
                dayIn.setAlpha(0.12f);
                dayOut.setAlpha(0.12f);
            }

        });
    }
}
