package com.playgilround.schedule.client.detailschedule;

import android.content.Context;

/**
 * 19-03-10
 * 디테일 스케줄 데이터 처리
 */
public class DetailSchedulePresenter implements DetailScheduleContract.Presenter {

    private static final String TAG = DetailSchedulePresenter.class.getSimpleName();

    private final DetailScheduleContract.View mView;
    private final Context mContext;

    DetailSchedulePresenter(Context context, DetailScheduleContract.View view) {
        mView = view;
        mContext = context;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
