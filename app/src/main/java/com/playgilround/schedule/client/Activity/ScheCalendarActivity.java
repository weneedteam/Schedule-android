package com.playgilround.schedule.client.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.playgilround.schedule.client.R;

/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 */
public class ScheCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    static final String TAG = ScheCalendarActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        setTitle("스케줄 달력");
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        //다음 달로 이동
        calendarView.setOnForwardPageChangeListener(() -> Log.d(TAG, "Next Month ---"));

        //전 달로 이동
        calendarView.setOnPreviousPageChangeListener(() -> Log.d(TAG, "Previous Month ---"));

    }
}
