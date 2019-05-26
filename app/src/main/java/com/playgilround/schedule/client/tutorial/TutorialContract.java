package com.playgilround.schedule.client.tutorial;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

/**
 * 19-02-20
 * MVP Pattern
 * Contract -> View, Presenter에 대한 interface
 */
public interface TutorialContract {

    interface View extends BaseView<Presenter> {

        void skipTutorial();

    }

    interface Presenter extends BasePresenter {

        void checkLogin();

    }
}
