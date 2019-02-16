package com.playgilround.schedule.client.schedule.info;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.model.ScheduleInfo;

import java.util.ArrayList;

/**
 * 19-02-13
 * MVP Pattern
 * Contract -> View, Presenter에 대한 interface
 */
public interface ScheduleInfoContract {

    interface View extends BaseView<Presenter> {

        // 스케줄 정보 얻기 완료
        void onGetSuccessInfo(ArrayList<ScheduleInfo> arrInfo);

        void onItemClick(int id);
    }

    interface Presenter extends BasePresenter {

        // 오늘 저장된 스케줄 얻기
        void getTodaySchedule(String dateString);
    }

}
