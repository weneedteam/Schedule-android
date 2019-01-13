package com.playgilround.schedule.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 * Test
 */
public class ScheduleCalendarActivity extends AppCompatActivity {

    static final String TAG = ScheduleCalendarActivity.class.getSimpleName();

    String strMDay;
    int strMYear, strMonth;
    Realm realm;

    CalendarView calendarView;
    List<EventDay> events;
    private RealmResults<Schedule> realmSchedule; //저장된 스케줄 RealmResults

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setTitle(getString(R.string.text_schedule_calendar));
        events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_3));
        realm = Realm.getDefaultInstance();

        calendarView = findViewById(R.id.calendarView);



        //날짜 클릭 시 다이얼로그
        calendarView.setOnDayClickListener(eventDay -> showDialogCalendar(eventDay.getCalendar().getTime().toString()));

        //다음 달로 이동
//        calendarView.setOnForwardPageChangeListener(() -> Log.d(TAG, "Next Month ---") );
        calendarView.setOnForwardPageChangeListener(() -> {
            getScheduleRealm();
            Log.d(TAG, "Forward Month ---");

        });

        //전 달로 이동
        calendarView.setOnPreviousPageChangeListener(() -> {
            getScheduleRealm();
            Log.d(TAG, "Previous Month ---");
        });

        getScheduleRealm();
    }

    //yyyy-MM 기준으로 저장된 스케줄 표시
    private void getScheduleRealm() {

        //현재 페이지 달력 시간 얻기
        long currentPageTime = calendarView.getCurrentPageDate().getTimeInMillis();
        DateTime dateTime = new DateTime(Long.valueOf(currentPageTime), DateTimeZone.UTC);

        strMYear = dateTime.plusHours(9).getYear(); //연도
        strMonth = dateTime.plusHours(9).getMonthOfYear() -1; //달
        strMDay = dateTime.plusHours(9).toString(getString(R.string.text_date_year_month)); //yyyy-MM

        realm.executeTransaction(realm -> {
            realmSchedule = realm.where(Schedule.class).equalTo("date", strMDay).findAll();
            //해당 yyyy-MM 에 저장된 스케줄 dd 얻기.
            for (Schedule schedule : realmSchedule) {
                DateTime realmTime = new DateTime(Long.valueOf(schedule.getTime()), DateTimeZone.UTC);

                int realmDay = realmTime.plusHours(9).getDayOfMonth();

                Calendar realmCalendar = Calendar.getInstance();
                realmCalendar.set(Calendar.YEAR, strMYear);
                realmCalendar.set(Calendar.MONTH, strMonth);
                realmCalendar.set(Calendar.DATE, realmDay);
                events.add(new EventDay(realmCalendar, R.mipmap.schedule_star));
            }
            calendarView.setEvents(events);

        });
    }

    //show Dialog When User Click Calendar
    private void showDialogCalendar(String date) {
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
