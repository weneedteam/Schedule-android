package com.playgilround.schedule.client.schedule;

import com.applandeo.materialcalendarview.EventDay;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

import io.realm.Realm;

/**
 * 19-02-12
 * MVP Pattern
 * Contract -> View와 Presenter에 대한 interface을 작성
 */
public interface ScheduleContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void getScheduleRealm(Realm realm); //저장된 스케줄 얻기

        // 다이얼로그가 필요한 날짜 형태 변환
        String convertCalendarToDateString(EventDay eventDay);

    }
}
