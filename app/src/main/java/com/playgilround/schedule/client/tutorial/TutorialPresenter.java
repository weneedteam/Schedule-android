package com.playgilround.schedule.client.tutorial;

import com.playgilround.schedule.client.infoschedule.InfoScheduleContract;

import io.realm.Realm;

/**
 * 19-02-20
 * Tutorial 관련 데이터 처리 Presenter
 */
public class TutorialPresenter implements TutorialContract.Presenter {

    private final TutorialContract.View mView;

    TutorialPresenter(TutorialContract.View view) {
        mView = view;

        mView.setPresenter(this);
    }
    @Override
    public void start() {

    }
}
