package com.playgilround.schedule.client.manyschedule;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

import java.util.ArrayList;

/**
 * 19-02-18
 * Contract -> View와 Presenter에 대한 interface를 작
 */
public interface ManyScheduleContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
