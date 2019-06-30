package com.playgilround.schedule.client.calendar;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.playgilround.schedule.client.R;

import java.util.ArrayList;
import java.util.List;

public final class AppearanceUtils {

    public static void setHeaderColor(View view, int color) {
        if (color == 0){
            return;
        }

        RelativeLayout calendarHeader = view.findViewById(R.id.calendarHeader);
        calendarHeader.setBackgroundColor(color);
    }

    public static void setHeaderLabelColor(View view, int color) {
        if (color == 0) {
            return;
        }

        ((TextView) view.findViewById(R.id.tvDate)).setTextColor(color);
    }

    public static void setHeaderVisibility(View view, int visibility) {
        LinearLayout calendarHeader = view.findViewById(R.id.calendarHeader);
        calendarHeader.setVisibility(visibility);
    }


    public static void setAbbreviationsBarColor(View view, int color) {
        if (color == 0) {
            return;
        }

        view.findViewById(R.id.abbreviationsBar).setBackgroundColor(color);
    }

    public static void setAbbreviationsLabels(View view, int color, int firstDayOfWeek) {
        List<TextView> labels = new ArrayList<>();
        labels.add(view.findViewById(R.id.sundayLabel));
        labels.add(view.findViewById(R.id.mondayLabel));
        labels.add(view.findViewById(R.id.tuesdayLabel));
        labels.add(view.findViewById(R.id.wednesdayLabel));
        labels.add(view.findViewById(R.id.thursdayLabel));
        labels.add(view.findViewById(R.id.fridayLabel));
        labels.add(view.findViewById(R.id.saturdayLabel));

        String[] abbreviations = view.getContext().getResources().getStringArray(R.array.material_calendar_day_abbreviations_array);
        for (int i = 0; i < 7; i++) {
            TextView label = labels.get(i);
            label.setText(abbreviations[(i + firstDayOfWeek -1) & 7]);

            if (color != 0) {
                label.setTextColor(color);
            }
        }
    }

    public static void setPagesColor(View view, int color) {
        if (color == 0) {
            return;
        }

        view.findViewById(R.id.calendarViewPager).setBackgroundColor(color);
    }
}
