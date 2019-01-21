package com.playgilround.schedule.client.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
public class ScheduleCalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final String TAG = ScheduleCalendarActivity.class.getSimpleName();

    String strMDay;
    int strMYear, strMonth;
    Realm realm;

    CalendarView calendarView;
    List<EventDay> events;
    private RealmResults<Schedule> realmSchedule; //저장된 스케줄 RealmResults

    String currentCalendar = "";
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setTitle(getString(R.string.text_schedule_calendar));
        events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_3));
        realm = Realm.getDefaultInstance();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.naviView);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);
        calendarView = findViewById(R.id.calendarView);


        //날짜 클릭 시 다이얼로그
        calendarView.setOnDayClickListener(eventDay -> {
            //유저가 클릭한 날짜와, 현재 클릭되어있는 날짜가 같을 경우에만 Dialog 표시
            if (currentCalendar.equals(eventDay.getCalendar().getTime().toString())) {
                showDialogCalendar(eventDay.getCalendar().getTime().toString());
            }
            currentCalendar = eventDay.getCalendar().getTime().toString();

        });

        //다음 달로 이동
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
        strMonth = dateTime.plusHours(9).getMonthOfYear() - 1; //달
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navCalendar) {
            startActivity(new Intent(this, ScheduleCalendarActivity.class));
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Navigation
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
