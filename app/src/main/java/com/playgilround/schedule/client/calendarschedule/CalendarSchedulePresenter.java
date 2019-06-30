package com.playgilround.schedule.client.calendarschedule;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendar.EventDay;
import com.playgilround.schedule.client.model.MonthEnum;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 19-02-12
 * 데이터 처리 MVP Presenter
 */
public class CalendarSchedulePresenter implements CalendarScheduleContract.Presenter {

    private static final String TAG = CalendarSchedulePresenter.class.getSimpleName();

    private final Realm mRealm;
    private final Context mContext;
    private final CalendarScheduleContract.View mView;

    private List<EventDay> mEvents;

    private ArrayList<String> arrManyDays;

    private String mCurrentCalenderString = "";

    private int retCount;

    CalendarSchedulePresenter(Context context, CalendarScheduleContract.View view) {
        mView = view;
        mContext = context;
        mRealm = Realm.getDefaultInstance();

        mEvents = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        mEvents.add(new EventDay(calendar, R.drawable.sample_icon_3));

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "start Presenter.");
    }

    // yyyy-MM 기준으로 저장된 스케줄 표시
    @Override
    public void getSchedule(long currentPageDate) {

        mRealm.executeTransaction(realm -> {

            DateTime currentDateTime = new DateTime(currentPageDate, DateTimeZone.getDefault());

            int year = currentDateTime.getYear();
            int month = currentDateTime.getMonthOfYear() - 1;

            String date = currentDateTime.toString(mContext.getString(R.string.text_date_year_month));
            RealmResults<Schedule> result = realm.where(Schedule.class).equalTo("date", date).findAll();

            mEvents = new ArrayList<>();
            // 해당 yyyy-MM 에 저장된 스케줄 dd 얻기
            for (int i = 0; i < result.size(); i++) {
                DateTime scheduleDateTime = new DateTime(result.get(i).getTime(), DateTimeZone.getDefault());

                int day = scheduleDateTime.getDayOfMonth();

                for (int j = 0; j < result.size(); j++) {
                    if (result.get(i).getDateDay().equals(result.get(j).getDateDay())) {
                        retCount++;
                    }
                }
                Calendar realmCalendar = Calendar.getInstance();
                realmCalendar.set(Calendar.YEAR, year);
                realmCalendar.set(Calendar.MONTH, month);
                realmCalendar.set(Calendar.DATE, day);

                if (retCount == 1) {
                    mEvents.add(new EventDay(realmCalendar, mView.getOneIcon(mContext)));
                } else if (retCount == 2) {
                    mEvents.add(new EventDay(realmCalendar, mView.getTwoIcon(mContext)));
                } else if (retCount == 3) {
                    mEvents.add(new EventDay(realmCalendar, mView.getThreeIcon(mContext)));
                } else if (retCount == 4) {
                    mEvents.add(new EventDay(realmCalendar, mView.getFourIcon(mContext)));
                } else {
                    mEvents.add(new EventDay(realmCalendar, mView.getFourIcon(mContext)));
                }
                retCount = 0;
            }
            mView.addEvents(mEvents);
        });

    }

    // 다이얼로그가 필요한 형태로 날짜
    @Override
    public String convertCalendarToDateString(EventDay eventDay) {
        Calendar calendar = eventDay.getCalendar();
        if (calendar != null) {
            if (mCurrentCalenderString.equals(calendar.getTime().toString())) {
                // 공백 기준
                String[] date_arr = mCurrentCalenderString.split(" ");

                // Readme:: year + month + day
                mCurrentCalenderString = "";
                return date_arr[5] + "-" + MonthEnum.getMonthNum(date_arr[1]) + "-" + date_arr[2];
            }
            mCurrentCalenderString = calendar.getTime().toString();
            return "";
        }
        return null;
    }

    // 캘린더에서 선택한 날짜 (다중) 확인
    @Override
    public ArrayList<String> getSelectedManyDays(List<Calendar> dates) {
        if (dates.size() > 0) {
            arrManyDays = new ArrayList<>();
            for (Calendar selectTime : dates) {
                try {
                    long milliseconds = new SimpleDateFormat(mContext.getString(R.string.text_date_all_format), Locale.ENGLISH)
                            .parse(selectTime.getTime().toString()).getTime();

                    DateTime dateTime = new DateTime(milliseconds, DateTimeZone.getDefault());
                    String strManyDay = dateTime.toString(mContext.getString(R.string.text_date_year_month_day));

                    arrManyDays.add(strManyDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrManyDays;
    }

    @Override
    public void realmClose() {
        mRealm.close();
    }

    @Override
    public void setFloatingAnimation(Animation open, Animation close) {
        open = AnimationUtils.loadAnimation(mContext, R.anim.floating_open);
        close = AnimationUtils.loadAnimation(mContext, R.anim.floating_close);

        mView.eventFloating(open, close);
    }
}
