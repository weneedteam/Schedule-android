package com.playgilround.schedule.client.schedule.add;

import android.content.Context;
import android.util.Log;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private static final String SCHEDULE_SAVE_FAIL = "fail";
    private static final String SCHEDULE_SAVE_SUCCESS = "SUCCESS";


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
    public void confirm(ArrayList<String> arrDate, ArrayList<String> arrDateDay, String title, String desc, String time,
                        double latitude, double longitude, String location) {

        if (title.length() == 0) {
            mView.onScheduleSave(SCHEDULE_SAVE_FAIL);
        } else {
            mRealm.executeTransaction(realm -> {
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
            });
            mView.onScheduleSave(SCHEDULE_SAVE_SUCCESS);
        }

        Log.d(TAG, "Confirm presenter..");
    }

    @Override
    public void realmClose() {
        mRealm.close();
    }
}
