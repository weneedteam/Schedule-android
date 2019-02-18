package com.playgilround.schedule.client.manyschedule;

import java.util.ArrayList;

/**
 * 19-02-18
 * 다중 스케줄 관련 데이터 처리 Presenter
 */

public class ManySchedulePresenter implements ManyScheduleContract.Presenter {

    private static final String TAG = ManySchedulePresenter.class.getSimpleName();

    private final ManyScheduleContract.View mView;

    ManySchedulePresenter(ManyScheduleContract.View view) {
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
