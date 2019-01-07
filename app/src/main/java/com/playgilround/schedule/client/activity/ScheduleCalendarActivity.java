package com.playgilround.schedule.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.playgilround.schedule.client.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 * Test
 */
public class ScheduleCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    static final String TAG = ScheduleCalendarActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setTitle(getString(R.string.text_schedule_calendar));
        calendarView = findViewById(R.id.calendarView);

        List<EventDay> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_3));
        calendarView.setEvents(events);
        //날짜 클릭 시 다이얼로그
        calendarView.setOnDayClickListener(eventDay -> showDialogCalendar(eventDay.getCalendar().getTime().toString()));

        //다음 달로 이동
        calendarView.setOnForwardPageChangeListener(() -> Log.d(TAG, "Next Month ---"));

        //전 달로 이동
        calendarView.setOnPreviousPageChangeListener(() -> Log.d(TAG, "Previous Month ---"));

        Log.d(TAG, "calendarView.getCurrentPageDate() -->" + calendarView.getCurrentPageDate().getTimeInMillis());
    }

    //show Dialog When User Click Calendar
    private void showDialogCalendar(String date) {
     /*   DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;*/

        String[] date_arr = date.split(" "); //공백 기준

        String strYear = date_arr[5];
        String strMonth = monthChange(date_arr[1]);
        String strDay = date_arr[2];

        String strDate = strYear + strMonth + strDay;

        Intent intent = new Intent(this, ScheduleInfoActivity.class);
        intent.putExtra("date", strDate);
        startActivity(intent);

/*
        ScheduleInfoActivity calendarDialog = new ScheduleInfoActivity(this, this, strDate);

        WindowManager.LayoutParams wm = calendarDialog.getWindow().getAttributes();
        wm.copyFrom(calendarDialog.getWindow().getAttributes());

        wm.width = (int) (width / 1.2);
        wm.height = (int) (height / 1.5);

        calendarDialog.show();*/
    }

    //Month Eng -> Num
    private String monthChange(String month) {
        String result = "";
        switch (month) {
            case "Jan":
                result = "01";
                break;
            case "Feb":
                result = "02";
                break;
            case "Mar":
                result = "03";
                break;
            case "Apr":
                result = "04";
                break;
            case "May":
                result = "05";
                break;
            case "Jun":
                result = "06";
                break;
            case "Jul":
                result = "07";
                break;
            case "Aug":
                result = "08";
                break;
            case "Sep":
                result = "09";
                break;
            case "Oct":
                result = "10";
                break;
            case "Nov":
                result = "11";
                break;
            case "Dec":
                result = "12";
                break;
        }
        return result;
    }
}
