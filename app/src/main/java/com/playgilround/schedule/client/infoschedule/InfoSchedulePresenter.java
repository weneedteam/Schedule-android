package com.playgilround.schedule.client.infoschedule;

import android.util.Log;

import com.playgilround.schedule.client.model.Schedule;
import com.playgilround.schedule.client.model.ScheduleInfo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 19-02-13
 * Schedule Info 관련 데이터 처리 Presenter
 */
public class InfoSchedulePresenter implements InfoScheduleContract.Presenter {

    private static final String TAG = InfoSchedulePresenter.class.getSimpleName();

    private final Realm mRealm;
    private final InfoScheduleContract.View mView;

    private ArrayList<ScheduleInfo> arrCard;

    InfoSchedulePresenter(InfoScheduleContract.View view) {
        mView = view;
        mRealm = Realm.getDefaultInstance();

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "start Presenter.");
    }

    // 오늘 기준으로 저장된 스케줄 얻기
    @Override
    public void getTodaySchedule(String dateString) {
        mRealm.executeTransaction(realm -> {
            RealmResults<Schedule> result = realm.where(Schedule.class).equalTo("dateDay", dateString).findAll();

            if (result.size() != 0) {
                arrCard = new ArrayList<>();
                for (Schedule schedule : result) {
                    arrCard.add(new ScheduleInfo(schedule.getId(), schedule.getTime(), schedule.getTitle(), schedule.getDesc()));
                }
                mView.onGetSuccessInfo(arrCard);
            }
        });
    }
}
