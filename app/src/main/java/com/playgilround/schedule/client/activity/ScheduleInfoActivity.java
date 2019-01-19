package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.ScheduleCardAdapter;
import com.playgilround.schedule.client.model.Schedule;
import com.playgilround.schedule.client.model.ScheduleCard;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 18-12-27
 * Calendar 에서 날짜 클릭 시 스케줄 정보가 표시되는 액티비티
 */
public class ScheduleInfoActivity extends Activity implements View.OnClickListener/*, ScheduleCardAdapter.Listener*/ {


    private String date;
    private String strDateDay;
    private ArrayList<Object> arrTitle;
    private ArrayList<Object> arrDesc;
    private ArrayList<Long> arrTime;

    static final String TAG = ScheduleInfoActivity.class.getSimpleName();

    public static final int ADD_SCHEDULE = 1000;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ScheduleCardAdapter mAdapter;
    private ArrayList<ScheduleCard> arrCard;

    Realm realm;
    public static final String INTENT_EXTRA_DATE = "date";
    RealmResults<Schedule> realmSchedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_calendar);

        realm = Realm.getDefaultInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        date = intent.getStringExtra(INTENT_EXTRA_DATE);
        Button cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(l -> finish());

        TextView tvDate = findViewById(R.id.tv_date);
        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";
        tvDate.setText(strDate);

        strDateDay = strYear + "-" + strMonth + "-" + strDay;

        findViewById(R.id.ivAddBtn).setOnClickListener(this);

        getTodaySchedule(realm);

    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        WindowManager.LayoutParams wm = getWindow().getAttributes();

        wm.copyFrom(getWindow().getAttributes());
        wm.width = (int) (width / 1.2);
        wm.height = (int) (height / 1.5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddBtn:
                Intent intent = new Intent(this, AddScheduleActivity.class);
                intent.putExtra("date", date);
                startActivityForResult(intent, ADD_SCHEDULE);
        }
    }

    //CardView Click
  /*  @Override
    public void onItemClick(ScheduleCard schedule) {
        if (schedule != null) {
            Toast.makeText(getApplicationContext(), "Schedule Click ->" + schedule.title, Toast.LENGTH_LONG).show();
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                //스케줄 입력이 완료됬을 때
                break;
        }
    }

    //오늘 저장된 스케줄 정보 얻기
    private void getTodaySchedule(Realm realm) {
        realm.executeTransaction(realm1 -> {
            realmSchedule = realm.where(Schedule.class).equalTo("dateDay", strDateDay).findAll();

            if (realmSchedule.size() != 0) {
                arrTime = new ArrayList<>();
                arrTitle = new ArrayList<>();
                arrDesc = new ArrayList<>();

                for (Schedule schedule : realmSchedule) {
                    arrTime.add(schedule.getTime());
                    arrTitle.add(schedule.getTitle());
                    arrDesc.add(schedule.getDesc());
                }
                mAdapter = new ScheduleCardAdapter(arrTime, arrTitle, arrDesc);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}
