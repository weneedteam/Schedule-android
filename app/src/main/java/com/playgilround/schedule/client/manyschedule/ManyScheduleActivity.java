package com.playgilround.schedule.client.manyschedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.ManyScheduleAdapter;
import com.playgilround.schedule.client.model.Schedule;
import com.playgilround.schedule.client.addschedule.AddScheduleActivity;


import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

import static com.playgilround.schedule.client.infoschedule.InfoScheduleActivity.ADD_SCHEDULE;

/**
 * 19-02-07
 * 스케줄 날짜 다중 선택 시,
 * 선택 한 날짜로 저장할 지 표시되는 Activity
 */
public class ManyScheduleActivity extends AppCompatActivity implements ManyScheduleContract.View {

    @BindView(R.id.tvInputSchedule)
    TextView tvInput;

    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.btnSave)
    Button btnSave;

    static final String TAG = ManyScheduleActivity.class.getSimpleName();

    ManyScheduleAdapter adapter;

    ArrayList<String> arrDay;
    String strInput;
    private String strDate;
    RealmResults<Schedule> realmSchedule;

    private ManyScheduleContract.Presenter mPresenter;

    public static final String INTENT_EXTRA_MANY_DATE = "manyDate";
    public static final String INTENT_EXTRA_INPUT_TEXT = "inputText";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.recycler_many_schedule);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        arrDay = intent.getStringArrayListExtra(INTENT_EXTRA_MANY_DATE);
        strDate = intent.getStringExtra(INTENT_EXTRA_INPUT_TEXT);

        tvInput.setText(strDate);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        new ManySchedulePresenter(this);
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
                    finish();
                    break;
                }
        }
    }

    @Override
    public void setPresenter(ManyScheduleContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
