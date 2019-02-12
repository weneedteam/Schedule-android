package com.playgilround.schedule.client.schedule;

import android.util.Log;

import com.playgilround.schedule.client.model.MonthEnum;

import io.realm.Realm;

/**
 * 19-02-12
 * 데이터 처리 MVP Presenter
 */
public class ScheduleCalendarPresenter implements ScheduleContract.Presenter {

    static final String TAG = ScheduleCalendarPresenter.class.getSimpleName();
    private final ScheduleContract.View mView;

    ScheduleCalendarPresenter(ScheduleContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "start Presenter.");
    }

    //yyyy-MM 기준으로 저장된 스케줄 표시
    @Override
    public void getScheduleRealm(Realm realm) {
        Log.d(TAG, "getScheduleRealm ->" + realm);
    }

    //다이얼로그가 필요한 형태로 날짜
    @Override
    public void setDialogDate(String date) {
        String[] date_arr = date.split(" "); //공백 기준

        String strYear = date_arr[5];
        String strMonth = MonthEnum.getMonthNum(date_arr[1]);
        String strDay = date_arr[2];

        String strDate = strYear + strMonth + strDay;

        mView.showDialogCalendar(strDate);
    }
}
