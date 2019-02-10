package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.ScheduleCardAdapter;
import com.playgilround.schedule.client.fragment.DetailScheduleFragment;
import com.playgilround.schedule.client.model.Schedule;
import com.playgilround.schedule.client.model.ScheduleCard;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 18-12-27
 * Calendar 에서 날짜 클릭 시 스케줄 정보가 표시되는 액티비티
 */
public class ScheduleInfoActivity extends Activity implements ScheduleCardAdapter.Listener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.tv_date)
    TextView tvDate;

    private String date;
    private String strDateDay;

    static final String TAG = ScheduleInfoActivity.class.getSimpleName();

    public static final int ADD_SCHEDULE = 1000;
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

        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        btnCancel.setOnClickListener(l -> finish());

        Intent intent = getIntent();
        date = intent.getStringExtra(INTENT_EXTRA_DATE);

        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";
        tvDate.setText(strDate);

        strDateDay = strYear + "-" + strMonth + "-" + strDay;

        getTodaySchedule(realm);

    }

    @OnClick(R.id.ivAddBtn)
    void onAddScheduleClick() {
        Intent intent = new Intent(this, AddScheduleActivity.class);
        intent.putExtra("date", date);
        startActivityForResult(intent, ADD_SCHEDULE);
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
        Toast.makeText(getApplicationContext(), "Schedule Click ->" + schedule, Toast.LENGTH_LONG).show();
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final DetailScheduleFragment fragment = DetailScheduleFragment.getInstance(date, schedule);
            final FragmentManager fm = getFragmentManager();
            fragment.show(fm, "TAG");
        } else {
            Toast.makeText(getApplicationContext(), "현재 위치를 얻기 위해 \n GPS 위치 기능을 켜주세요!", Toast.LENGTH_LONG).show();
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
                tvDate.setText(data.getStringExtra("date")); //변경 된 날짜로 표시
                strDateDay = data.getStringExtra("dateDay");

                getTodaySchedule(realm);
                break;
        }
    }

    //오늘 저장된 스케줄 정보 얻기
    private void getTodaySchedule(Realm realm) {
        realm.executeTransaction(realm1 -> {
            realmSchedule = realm.where(Schedule.class).equalTo("dateDay", strDateDay).findAll();

            if (realmSchedule.size() != 0) {
                arrCard = new ArrayList<>();
                for (Schedule schedule : realmSchedule) {
                    arrCard.add(new ScheduleCard(schedule.getId(), schedule.getTime(), schedule.getTitle(), schedule.getDesc()));

                }

                mAdapter = new ScheduleCardAdapter(this, arrCard, this);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}
