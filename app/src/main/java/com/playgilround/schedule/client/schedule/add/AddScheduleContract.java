package com.playgilround.schedule.client.schedule.add;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

import java.util.ArrayList;

/**
 * 19-02-17
 * Add Schedule
 * Contract -> View, Presenter에 대한 interface
 */
public interface AddScheduleContract {

    interface View extends BaseView<Presenter> {

        // 스케줄 저장 완료
        void onScheduleSave(String state);
    }

    interface Presenter extends BasePresenter {

        // 확인 버튼 클릭 시 스케줄 저장
        void confirm(ArrayList<String> date, ArrayList<String> dateDay, String title, String desc, String time, double latitude, double longitude, String location);

        void realmClose();
    }
}
