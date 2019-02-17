package com.playgilround.schedule.client.addschedule;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 19-02-17
 * Add Schedule
 * Contract -> View, Presenter에 대한 interface
 */
public interface AddScheduleContract {

    interface View extends BaseView<Presenter> {

        // 스케줄 저장 완료
        void onScheduleSave(String state);

        // 스케줄 날짜 설정 완료
        void setDaySchedule(ArrayList<String> arrDateDay, String dateTitle, int size);

        // 스케줄 시간 설정 완료
        void setTimeSchedule(String dayTime, String time);
    }

    interface Presenter extends BasePresenter {

        // 확인 버튼 클릭 시 스케줄 저장
        void confirm(ArrayList<String> date, ArrayList<String> dateDay, String title, String desc, String time, double latitude, double longitude, String location);

        // 날짜 선택
        void onSelectDay(List<Calendar> calendars);

        // 시간 설정
        void onSelectTime(String date, long milliseconds);

        void realmClose();
    }
}
