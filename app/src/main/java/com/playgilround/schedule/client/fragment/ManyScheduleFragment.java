package com.playgilround.schedule.client.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.ManyScheduleAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-02-07
 * 스케줄 날짜 다중 선택 시,
 * 선택 한 날짜로 저장할 지 표시되는 DialogFragment
 */
public class ManyScheduleFragment extends DialogFragment {
    
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    ManyScheduleAdapter adapter;
    static ArrayList<String> arrDay;
    public static ManyScheduleFragment getInstance(ArrayList<String> day) {
        arrDay = day;

        return new ManyScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_many_schedule, container);
        ButterKnife.bind(this, rootView);

        mRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new ManyScheduleAdapter(arrDay);
        mRecycler.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
