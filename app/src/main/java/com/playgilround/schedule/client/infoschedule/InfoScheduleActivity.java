package com.playgilround.schedule.client.infoschedule;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.infoschedule.view.InfoScheduleAdapter;
import com.playgilround.schedule.client.detailschedule.DetailScheduleFragment;
import com.playgilround.schedule.client.model.ScheduleInfo;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 18-12-27
 * Calendar 에서 날짜 클릭 시 스케줄 정보가 표시되는 액티비티
 */
public class InfoScheduleActivity extends Activity implements InfoScheduleContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvdate)
    TextView tvDate;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    private String date;
    private String strDateDay;

    static final String TAG = InfoScheduleActivity.class.getSimpleName();

    public static final int ADD_SCHEDULE = 1000;
    RecyclerView.LayoutManager mLayoutManager;
    InfoScheduleAdapter mAdapter;

    public static final String INTENT_EXTRA_DATE = "date";

    private InfoScheduleContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_calendar);

        ButterKnife.bind(this);
        setFinishOnTouchOutside(false);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new InfoSchedulePresenter(this);

        Intent intent = getIntent();
        date = intent.getStringExtra(INTENT_EXTRA_DATE);

        String strYear = date.substring(0, 4);
        String strMonth = date.substring(5, 7);
        String strDay = date.substring(8, 10);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";
        tvDate.setText(strDate);

        strDateDay = strYear + "-" + strMonth + "-" + strDay;

        callTodaySchedule();

        btnCancel.setOnClickListener(view -> finish());
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

    //CardView Click
    @Override
    public void onItemClick(int schedule) {
        Log.d(TAG, "onItemClick ->" + schedule);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final DetailScheduleFragment fragment = DetailScheduleFragment.getInstance(date, schedule);
            final FragmentManager fm = getFragmentManager();
            fragment.show(fm, "TAG");
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_gps_enable), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                //스케줄 입력이 완료됬을 때
                if (data != null) {
                    tvDate.setText(data.getStringExtra("date")); //변경 된 날짜로 표시
                    strDateDay = data.getStringExtra("dateDay");
                }
                callTodaySchedule();
                break;
        }
    }

    // 오늘 저장된 스케줄 정보 얻기
    private void callTodaySchedule() {
        Log.d(TAG, "callTodaySchedule ->" + strDateDay);
        mPresenter.getTodaySchedule(strDateDay);
    }

    // 오늘 저장된 스케줄 정보 얻기 완료
    @Override
    public void onGetSuccessInfo(ArrayList<ScheduleInfo> arrInfo) {
        mAdapter = new InfoScheduleAdapter(this, arrInfo, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    // 실제 View 생성 후.
    @Override
    public void setPresenter(InfoScheduleContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
