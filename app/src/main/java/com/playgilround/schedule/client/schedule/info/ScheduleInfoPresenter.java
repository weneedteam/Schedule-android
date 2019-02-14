package com.playgilround.schedule.client.schedule.info;

import android.util.Log;

import com.playgilround.schedule.client.adapter.ScheduleInfoAdapter;
import com.playgilround.schedule.client.model.Schedule;
import com.playgilround.schedule.client.model.ScheduleInfo;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 19-02-13
 * Schedule Info 관련 데이터 처리 Presenter
 */
public class ScheduleInfoPresenter implements ScheduleInfoContract.Presenter {

    private static final String TAG = ScheduleInfoPresenter.class.getSimpleName();

    private final Realm mRealm;
    private final ScheduleInfoContract.View mView;

    private ArrayList<ScheduleInfo> arrCard;

    ScheduleInfoPresenter(ScheduleInfoContract.View view) {
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

  /*  // Schedule 정보 세팅
    @Override
    public void onBindViewScheduleInfo(int position) {
        ScheduleInfo info = arrCard.get(position);
        Log.d(TAG, "onBindView Schedule ->" + info.title);
        mView.setScheduleInfo(info);
    }

    @Override
    public int getScheduleCount() {
        return arrCard.size();
    }*/

    @Override
    public void onItemClick(int id) {

    }

}
