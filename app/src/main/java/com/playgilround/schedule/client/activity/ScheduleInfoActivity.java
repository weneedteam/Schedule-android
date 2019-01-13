package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.ScheduleCardAdapter;

/**
 * 18-12-27
 * Calendar 에서 날짜 클릭 시 스케줄 정보가 표시되는 액티비티
 */
public class ScheduleInfoActivity extends Activity implements View.OnClickListener {


    private String date;

    static final String TAG = ScheduleInfoActivity.class.getSimpleName();

    public static final int ADD_SCHEDULE = 1000;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ScheduleCardAdapter mAdapter;

    public static final String INTENT_EXTRA_DATE = "location";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_calendar);

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

        findViewById(R.id.ivAddBtn).setOnClickListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                //스케줄 입력이 완료됬을 때
                break;
        }
    }

}
