package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;

import java.util.Date;

import io.realm.Realm;

/**
 * 18-12-30
 * 스케줄 추가 관련 DialogFragment
 */
public class AddScheduleActivity extends Activity {

    static final String TAG = AddScheduleActivity.class.getSimpleName();

    TextView tvDate, tvTime;
    Button btnConfirm;
    Realm realm;

    EditText etTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_add_schedule);
//        final LocationManager lm = (LocationManager)

        realm = Realm.getDefaultInstance();

        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tvScheduleTime);

        btnConfirm = findViewById(R.id.btn_confirm);
        etTitle = findViewById(R.id.etScheduleTitle);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        Log.d(TAG, "Date Test ->" + date);

        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";

        //Get Current Time
        DateTime dateTime = new DateTime();
        String curTime = dateTime.toString("HH:mm");
        Log.d(TAG, "curTime ->" + curTime);

        String strTime = strYear + "-" + strMonth + "-" + strDay + " " + curTime;
        tvDate.setText(strDate);
        tvTime.setText(strTime);

        btnConfirm.setOnClickListener(l -> {
            confirm();
        });

    }

    //Click Confirm Button
    private void confirm() {
        if (etTitle.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "스케줄을 입력해주세요!", Toast.LENGTH_LONG).show();
        } else {
            realm.executeTransaction(realm -> {
                Number currentIdNum = realm.where(Schedule.class).max("id");

                int nextId;

                if (currentIdNum == null) {
                    nextId = 0;
                } else {
                    nextId = currentIdNum.intValue() +1;
                }
                Log.d(TAG, "realm confirm" + nextId);

//                String checkDate = dt.format(new Date());
                Schedule mSchedule =  realm.createObject(Schedule.class, nextId);
//                mSchedule.setId(nextId);
                mSchedule.setTitle(etTitle.getText().toString());
//                mSchedule

                Toast.makeText(getApplicationContext(), "스케줄 저장 완료", Toast.LENGTH_LONG).show();
                finish();
            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
