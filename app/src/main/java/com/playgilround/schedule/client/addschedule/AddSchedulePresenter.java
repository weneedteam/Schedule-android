package com.playgilround.schedule.client.addschedule;

import android.content.Context;
import android.util.Log;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

/**
 * 19-02-17
 * 스케줄 추가 관련 데이터 처리 Presenter
 */
public class AddSchedulePresenter implements AddScheduleContract.Presenter {

    private static final String TAG = AddSchedulePresenter.class.getSimpleName();

    private final Realm mRealm;
    private final AddScheduleContract.View mView;
    private final Context mContext;

    public static final String SCHEDULE_SAVE_FAIL = "fail";
    public static final String SCHEDULE_SAVE_SUCCESS = "SUCCESS";


    AddSchedulePresenter(Context context, AddScheduleContract.View view) {
        mView = view;
        mContext = context;
        mRealm = Realm.getDefaultInstance();

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void confirm(int scheduleId, ArrayList<String> arrDate, ArrayList<String> arrDateDay, String title, String desc, String time,
                        double latitude, double longitude, String location) {

        if (title.length() == 0) {
            mView.onScheduleSave(SCHEDULE_SAVE_FAIL);
        } else {
            mRealm.executeTransaction(realm -> {
                if (arrDateDay.size() != 0) {
                    for (int i = 0; i < arrDateDay.size(); i++) {

                        Number currentIdNum = realm.where(Schedule.class).max("id");
                        int nextId;

                        if (currentIdNum == null) {
                            nextId = 0;
                        } else {
                            nextId = currentIdNum.intValue() + 1;
                        }

                        Schedule mSchedule = realm.createObject(Schedule.class, nextId);
                        mSchedule.setTitle(title);
                        mSchedule.setDate(arrDate.get(i));
                        mSchedule.setDateDay(arrDateDay.get(i));
                        mSchedule.setLocation(location);
                        mSchedule.setLatitude(latitude);
                        mSchedule.setLongitude(longitude);
                        mSchedule.setDesc(desc);
                        try {
                            String strTime = arrDateDay.get(i) + " " + time;
                            Date date = new SimpleDateFormat(mContext.getString(R.string.text_date_day_time), Locale.ENGLISH).parse(strTime);
                            long millisecond = date.getTime();
                            mSchedule.setTime(millisecond);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //MODIFY
                    Schedule mSchedule = realm.where(Schedule.class).equalTo("id", scheduleId).findFirst();
                    mSchedule.setTitle(title);
                }
            });
            mView.onScheduleSave(SCHEDULE_SAVE_SUCCESS);
        }
    }

    @Override
    public void onSelectDay(List<Calendar> calendars) {
        ArrayList<String> arrDateDay = new ArrayList<>();
        ArrayList<String> arrDate = new ArrayList<>();

        String strDateTime;
        String strDateTitle = "";

        String strMYearMonth;
        int chooseSize;

        try {
            for (Calendar calendar : calendars) {
                String strSelect = calendar.getTime().toString();
                Date date = new SimpleDateFormat(mContext.getString(R.string.text_date_all_format), Locale.ENGLISH).parse(strSelect);
                long milliseconds = date.getTime();

                DateTime dateTime = new DateTime(milliseconds, DateTimeZone.getDefault());

                strDateTime = dateTime.toString(mContext.getString(R.string.text_date_year_month_day));
                strDateTitle = dateTime.toString(mContext.getString(R.string.text_date_year_month_day_title));

                String strYear = strDateTime.substring(0, 4);
                String strMonth = strDateTime.substring(5, 7);

                strMYearMonth = strYear + "-" + strMonth;
                arrDate.add(strMYearMonth);
                arrDateDay.add(strDateTime);
            }
            // 스케줄 다중 선택
            if (calendars.size() > 1) {
                chooseSize = calendars.size();
                mView.setDaySchedule(arrDateDay, strDateTitle, chooseSize);
            } else {
                chooseSize = 1;
                mView.setDaySchedule(arrDateDay, strDateTitle, chooseSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectTime(String date, long milliseconds) {
        String strTime;

        DateTime dateTime = new DateTime(milliseconds, DateTimeZone.getDefault());
        strTime = dateTime.toString(mContext.getString(R.string.text_date_time));
        String strDayTime = date + " " + strTime;

        mView.setTimeSchedule(strDayTime, strTime);
    }

    @Override
    public void getScheduleInfo(int id) {
        mRealm.executeTransaction(realm -> {
            Schedule schedule = realm.where(Schedule.class).equalTo("id", id).findFirst();
            Log.d(TAG, "Schedule ->" + schedule.getTitle());

            mView.setScheduleInfo(schedule);
        });
    }

    @Override
    public void realmClose() {
        mRealm.close();
    }
}
