package com.playgilround.schedule.client.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.Schedule;

import io.realm.Realm;

/**
 * 19-01-23
 * 저장된 스케줄 정보가 나오는 Activity
 */
public class DetailScheduleFragment extends android.app.DialogFragment {

    static String strDate;
    static int scheduleId;
    Realm realm;

    static final String TAG = DetailScheduleFragment.class.getSimpleName();

    public static DetailScheduleFragment getInstance(String date, int id) {
        strDate = date;
        scheduleId = id;

        return new DetailScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_schedule, container);

        Log.d(TAG, "strDate -> " + strDate + "//" + scheduleId);
        findScheduleInfo();
        return rootView;
    }

    private void findScheduleInfo() {
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm -> {
            Schedule schedule = realm.where(Schedule.class).equalTo("id", scheduleId).findFirst();
            Log.d(TAG, "Schedule result ->" + schedule.getTitle());
        });
    }
}
