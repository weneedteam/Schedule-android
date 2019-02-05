package com.playgilround.schedule.client.activity;

import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 19-02-05
 * 다중 날짜 지정이 가능한 스케줄 액티비티.
 */
public class ManyDayScheduleCalendarActivity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    String mDay;
    int mYear, mMonth;

    Realm realm;
    private RealmResults<Schedule> realmSchedule;

    static final String TAG = ManyDayScheduleCalendarActivity.class.getSimpleName();

    List<EventDay> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_calendar);


        ButterKnife.bind(this);
        setTitle(R.string.text_schedule_calendar);

        events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_3));

        realm = Realm.getDefaultInstance();

        calendarView.setOnForwardPageChangeListener(this::getScheduleRealm);

        calendarView.setOnPreviousPageChangeListener(this::getScheduleRealm);
    }

    //yyyy-MM 기준으로 저장된 스케줄 표시
    private void getScheduleRealm() {
        long currentPageTime = calendarView.getCurrentPageDate().getTimeInMillis();
        DateTime dateTime = new DateTime(Long.valueOf(currentPageTime), DateTimeZone.UTC);

        mYear = dateTime.plusHours(9).getYear(); //연도
        mMonth = dateTime.plusHours(9).getMonthOfYear();
        mDay = dateTime.plusHours(9).toString(getString(R.string.text_date_year_month));

        Log.d(TAG, "Schedule Realm ->" + mYear + "//" + mMonth + "//" + mDay);

        realm.executeTransaction(realm -> {
            realmSchedule = realm.where(Schedule.class).equalTo("date", mDay).findAll();

            //해당 yyyy-MM에 저장된 스케줄 dd 얻기
            for (Schedule schedule : realmSchedule) {
                DateTime realmTime = new DateTime(Long.valueOf(schedule.getTime()), DateTimeZone.UTC);

                int realmDay = realmTime.plusHours(9).getDayOfMonth();
                Log.d(TAG, "get month -> " + realmDay);

                Calendar realmCalendar = Calendar.getInstance();
                realmCalendar.set(Calendar.YEAR, mYear);
                realmCalendar.set(Calendar.MONTH, mMonth);
                realmCalendar.set(Calendar.DATE, realmDay);
                events.add(new EventDay(realmCalendar, R.mipmap.schedule_star));
            }
            calendarView.setEvents(events);
        });
    }


}
