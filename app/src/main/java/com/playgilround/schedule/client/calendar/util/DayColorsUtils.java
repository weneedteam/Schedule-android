package com.playgilround.schedule.client.calendar.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendar.CalendarProperties;

import java.util.Calendar;

/**
 * 날짜 색깔 관련.
 */
public class DayColorsUtils {

    public static void setDayColors(TextView tv, int textColor, int typeface, int background) {
        if (tv == null) {
            return;
        }

        tv.setTypeface(null, typeface);
        tv.setTextColor(textColor);
        tv.setBackgroundResource(background);
    }

    public static void setHideDays(TextView tv, int textColor, int typeface, int background) {
        if (tv == null) {
            return;
        }

        tv.setTypeface(null, typeface);
        tv.setTextColor(textColor);
        tv.setBackgroundResource(background);
        tv.setVisibility(View.GONE);
    }

    /**
     * state 0 = Today
     * state 1 = first
     * state 2 = last
     * state 3 = 그 외
     * @param dayLabel
     * @param calendarProperties
     * @param state
     */
    public static void setSelectedDayColors(TextView dayLabel, CalendarProperties calendarProperties, int state) {
        dayLabel.setTypeface(null, Typeface.NORMAL);

        if (state == 0) {
            dayLabel.setTextColor(calendarProperties.getSelectionLabelColor());
            dayLabel.setBackgroundResource(R.drawable.background_color_circle_selector);
            dayLabel.getBackground().setColorFilter(calendarProperties.getSelectionColor(),
                    PorterDuff.Mode.MULTIPLY);
        } else if (state == 1) {
            dayLabel.setTextColor(Color.BLACK);
            dayLabel.setBackgroundResource(R.drawable.background_color_start_circle);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            dayLabel.setLayoutParams(params);
        } else if (state == 2) {
            dayLabel.setTextColor(Color.BLACK);
            dayLabel.setBackgroundResource(R.drawable.background_color_end_circle);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            dayLabel.setLayoutParams(params);
        } else if (state == 3) {
            dayLabel.setTextColor(Color.BLACK);
            dayLabel.setBackgroundResource(R.drawable.background_color_only_circle);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            dayLabel.setLayoutParams(params);
        } else {
            dayLabel.setTextColor(Color.BLACK);
            dayLabel.setBackgroundResource(R.drawable.background_color_square_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dayLabel.setLayoutParams(params);
        }

    }

    public static void setCurrentMonthDayColor(Calendar day, Calendar today, TextView tvLabel,
                                               CalendarProperties calendarProperties) {
        if (today.equals(day)) {
            DayColorsUtils.setSelectedDayColors(tvLabel, calendarProperties, 0);
        } else {
            setDayColors(tvLabel, calendarProperties.getDaysLabelsColor(), Typeface.NORMAL,
                    R.drawable.background_transparent);
        }
    }
}
