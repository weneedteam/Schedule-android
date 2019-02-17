package com.playgilround.schedule.client.calendarschedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.navigation.NavigationView;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.activity.FriendActivity;
import com.playgilround.schedule.client.activity.ManyScheduleActivity;
import com.playgilround.schedule.client.infoschedule.InfoScheduleActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.playgilround.schedule.client.infoschedule.InfoScheduleActivity.ADD_SCHEDULE;

/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 * Test
 */
public class CalendarScheduleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CalendarScheduleContract.View {

    static final String TAG = CalendarScheduleActivity.class.getSimpleName();

    @BindView(R.id.drawerLayout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.naviView)
    NavigationView navigationView;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @BindView(R.id.btn_save)
    Button saveBtn;

    private CalendarScheduleContract.Presenter mPresenter;

    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);
        setTitle(getString(R.string.text_schedule_calendar));

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

        new CalendarSchedulePresenter(this, this);

        // 날짜 클릭 시 다이얼로그
        calendarView.setOnDayClickListener(eventDay -> {
            // 유저가 클릭한 날짜와, 현재 클릭되어있는 날짜가 같을 경우에만 Dialog 표시
            String dateString = mPresenter.convertCalendarToDateString(eventDay);
            if (dateString == null) {
                // Todo:: Error message
                Log.e(TAG, "Convert Error");
            } else if (!dateString.isEmpty()) {
                showCalendarDialog(dateString);
            }
        });

        // 다음 달로 이동
        calendarView.setOnForwardPageChangeListener(this::callSchedules);

        // 전 달로 이동
        calendarView.setOnPreviousPageChangeListener(this::callSchedules);

        callSchedules();

        saveBtn.setOnClickListener(v -> {
            ArrayList arrManyDays = mPresenter.getSelectedManyDays(calendarView.getSelectedDates());
            if (arrManyDays == null) {
                // Todo:: Error message
                Log.e(TAG, "ManyDays is null");
            } else {
                Intent intent = new Intent(this, ManyScheduleActivity.class);
                intent.putExtra("manyDate", arrManyDays);
                startActivityForResult(intent, ADD_SCHEDULE);
            }
        });
    }

    // yyyy-MM 기준으로 저장된 스케줄 표시
    private void callSchedules() {
        mPresenter.getSchedule(calendarView.getCurrentPageDate().getTimeInMillis());
    }

    //show Dialog When User Click Calendar
    public void showCalendarDialog(String dateString) {
        // https://hashcode.co.kr/questions/3073/mvp-패턴에서-startactivity는-어디서-해야하나요
        Intent intent = new Intent(this, InfoScheduleActivity.class);
        intent.putExtra("date", dateString);
        startActivityForResult(intent, ADD_SCHEDULE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                // 스케줄 입력이 완료됬을 때
                callSchedules();
                calendarView.setSelectedDates(new ArrayList<>());
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navCalendar) {
            // 현재 캘린더 뷰라면 실행하지않도록 수정예정
            startActivity(new Intent(this, CalendarScheduleActivity.class));
        } else if (id == R.id.navFriend) {
            // finish();
            startActivity(new Intent(this, FriendActivity.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    //실제 View 가 만들어지는 시점
    @Override
    public void setPresenter(CalendarScheduleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.realmClose();
    }

    @Override
    public void addEvents(List<EventDay> events) {
        calendarView.setEvents(events);
    }
}
