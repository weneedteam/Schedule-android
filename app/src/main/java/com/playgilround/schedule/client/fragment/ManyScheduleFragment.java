package com.playgilround.schedule.client.fragment;

import android.app.DialogFragment;

import java.util.ArrayList;

/**
 * 19-02-07
 * 스케줄 날짜 다중 선택 시,
 * 선택 한 날짜로 저장할 지 표시되는 DialogFragment
 */
public class ManyScheduleFragment extends DialogFragment {

    static final String TAG = ManyScheduleFragment.class.getSimpleName();

    static ArrayList arrDay;
    public static ManyScheduleFragment getInstance(ArrayList day) {
        arrDay = day;

        return new ManyScheduleFragment();
    }
}
