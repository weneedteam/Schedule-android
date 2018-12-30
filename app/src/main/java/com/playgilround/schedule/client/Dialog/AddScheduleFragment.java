package com.playgilround.schedule.client.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;

/**
 * 18-12-30
 * 스케줄 추가 관련 DialogFragment
 */
public class AddScheduleFragment extends android.app.DialogFragment {

    static final String TAG = AddScheduleFragment.class.getSimpleName();

    TextView tvDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_schedule, container);

        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        String date = getArguments().getString("date");
        Log.d(TAG, "Date Test ->" + date);

        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";
        tvDate.setText(strDate);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
