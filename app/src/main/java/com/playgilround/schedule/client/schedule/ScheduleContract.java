package com.playgilround.schedule.client.schedule;

import com.applandeo.materialcalendarview.EventDay;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

import java.util.List;

/**
 * 19-02-12
 * MVP Pattern
 * Contract -> View와 Presenter에 대한 interface을 작성
 */
public interface ScheduleContract {

    interface View extends BaseView<Presenter> {

        void addEvents(List<EventDay> events);

    }

    interface Presenter extends BasePresenter {

        // 저장된 스케줄 얻기
        void getSchedule(long currentPageDate);

        // 다이얼로그가 필요한 날짜 형태 변환
        String convertCalendarToDateString(EventDay eventDay);

    }
}
