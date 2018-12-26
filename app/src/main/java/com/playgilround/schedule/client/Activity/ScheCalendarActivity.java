package com.playgilround.schedule.client.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import com.playgilround.schedule.client.R;


/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 * Test
 */
public class ScheCalendarActivity extends AppCompatActivity {

    private com.playgilround.schedule.client.Calendar.CalendarView calendarView;
    static final String TAG = ScheCalendarActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        setTitle("스케줄 달력");
        calendarView = findViewById(R.id.calendarView);

        //다음 달로 이동
        calendarView.setOnForwardPageChangeListener(() -> Log.d(TAG, "Next Month ---"));

        //전 달로 이동
        calendarView.setOnPreviousPageChangeListener(() -> Log.d(TAG, "Previous Month ---"));

    }
}
