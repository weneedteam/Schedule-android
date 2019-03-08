package com.playgilround.schedule.client.calendarschedule;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.applandeo.materialcalendarview.EventDay;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 19-02-12
 * MVP Pattern
 * Contract -> View와 Presenter에 대한 interface을 작성
 */
public interface CalendarScheduleContract {

    interface View extends BaseView<Presenter> {

        void addEvents(List<EventDay> events);

        Drawable getOneIcon(Context context);

        Drawable getTwoIcon(Context context);

        Drawable getThreeIcon(Context context);

        Drawable getFourIcon(Context context);
    }

    interface Presenter extends BasePresenter {

        // 저장된 스케줄 얻기
        void getSchedule(long currentPageDate);

        // 다이얼로그가 필요한 날짜 형태 변환
        String convertCalendarToDateString(EventDay eventDay);

        // 캘린더에서 선택한 날짜 (다중) 확인
        ArrayList<String> getSelectedManyDays(List<Calendar> dates);

        void realmClose();

    }
}
