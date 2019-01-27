package com.playgilround.schedule.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.navigation.NavigationView;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.MonthEnum;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.playgilround.schedule.client.activity.ScheduleInfoActivity.ADD_SCHEDULE;

/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 * Test
 */
public class ScheduleCalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final String TAG = ScheduleCalendarActivity.class.getSimpleName();

    @BindView(R.id.drawerLayout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.naviView)
    NavigationView navigationView;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    String strMDay;
    int strMYear, strMonth;
    Realm realm;

    List<EventDay> events;
    private RealmResults<Schedule> realmSchedule; //저장된 스케줄 RealmResults

    String currentCalendar = "";
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);
        setTitle(getString(R.string.text_schedule_calendar));
        events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_3));
        realm = Realm.getDefaultInstance();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

        //날짜 클릭 시 다이얼로그
        calendarView.setOnDayClickListener(eventDay -> {
            //유저가 클릭한 날짜와, 현재 클릭되어있는 날짜가 같을 경우에만 Dialog 표시
            if (currentCalendar.equals(eventDay.getCalendar().getTime().toString())) {
                showDialogCalendar(eventDay.getCalendar().getTime().toString());
            }
            currentCalendar = eventDay.getCalendar().getTime().toString();

        });

        //다음 달로 이동
        calendarView.setOnForwardPageChangeListener(this::getScheduleRealm);

        //전 달로 이동
        calendarView.setOnPreviousPageChangeListener(this::getScheduleRealm);

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
        String strMonth = MonthEnum.getMonthNum(date_arr[1]);
        String strDay = date_arr[2];

        String strDate = strYear + strMonth + strDay;

        Intent intent = new Intent(this, ScheduleInfoActivity.class);
        intent.putExtra("date", strDate);
        startActivityForResult(intent, ADD_SCHEDULE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                //스케줄 입력이 완료됬을 때
                getScheduleRealm();
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navCalendar) {
            //현재 캘린더 뷰라면 실행하지않도록 수정예정
            startActivity(new Intent(this, ScheduleCalendarActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Navigation
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
