package com.playgilround.schedule.client.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.ManyScheduleAdapter;
import com.playgilround.schedule.client.model.Schedule;

import java.util.ArrayList;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 19-02-07
 * 스케줄 날짜 다중 선택 시,
 * 선택 한 날짜로 저장할 지 표시되는 Activity
 */
public class ManyScheduleActivity extends AppCompatActivity {

    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.btn_save)
    Button btnSave;

    static final String TAG = ManyScheduleActivity.class.getSimpleName();

    ManyScheduleAdapter adapter;

    public static final int ADD_SCHEDULE = 1000;

    static ArrayList<String> arrDay;
    private String strDate;
    RealmResults<Schedule> realmSchedule;

    public static ManyScheduleActivity getInstance(ArrayList<String> day) {
        arrDay = day;

        return new ManyScheduleActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.recycler_many_schedule);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        arrDay = intent.getStringArrayListExtra("manyDate");
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        adapter = new ManyScheduleAdapter(arrDay);
        mRecycler.setAdapter(adapter);

        btnSave.setOnClickListener(l -> {
            Intent newIntent = new Intent(this, AddScheduleActivity.class);
            newIntent.putExtra("dateArr", arrDay);
            startActivityForResult(newIntent, ADD_SCHEDULE);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                //스케줄 입력이 완료됬을 때
                if (data != null) {
                    Log.d(TAG, "onActivityResult -----");
                    Realm realm = Realm.getDefaultInstance();
                    strDate = data.getStringExtra("date");
//                    getMonthSchedule(realm);
                    break;
                }
        }
    }
/*
    //이 달에 저장된 스케줄 얻기
    private void getMonthSchedule(Realm realm) {
        realm.executeTransaction(realm1 -> {
            Log.d(TAG, "strDateDay -> " + strDate);
            realmSchedule = realm.where(Schedule.class).equalTo("date", strDate).findAll();
            Log.d(TAG, "RealmSchedule size ->" + realmSchedule.size());

            for (Schedule schedule : realmSchedule) {
                DateTime realmTime = new Date
            }
        });
    }*/
}
