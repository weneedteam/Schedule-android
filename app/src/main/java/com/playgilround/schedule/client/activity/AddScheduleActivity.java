package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.playgilround.schedule.client.R;

import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import io.realm.Realm;

/**
 * 18-12-30
 * 스케줄 추가 관련 Activity
 */
public class AddScheduleActivity extends Activity implements View.OnClickListener, OnSelectDateListener {

    static final String TAG = AddScheduleActivity.class.getSimpleName();

    TextView tvDate, tvTime;
    Button btnConfirm;
    Realm realm;

    EditText etTitle;
    DateTime dateTime;
    DateTimeFormatter fmt;


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

        findViewById(R.id.llScheduleTime).setOnClickListener(this);
        findViewById(R.id.llScheduleLocation).setOnClickListener(this);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        Log.d(TAG, "Date Test ->" + date);

        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";

        //Get Current Time
        dateTime = new DateTime();
        String curTime = dateTime.toString("HH:mm");
        Log.d(TAG, "curTime ->" + curTime);

        String strTime = strYear + "-" + strMonth + "-" + strDay + " " + curTime;
        tvDate.setText(strDate);
        tvTime.setText(strTime);

        btnConfirm.setOnClickListener(l -> {
            confirm();
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llScheduleTime:
                showCalendarDialog();
                break;
            case R.id.llScheduleLocation:
                showLocationActivity();
                break;
        }
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

    //Show Select Calendar Dialog
    private void showCalendarDialog() {
        Log.d(TAG, "showCalendarDialog...");
        DatePickerBuilder dateBuilder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.ONE_DAY_PICKER)
                .headerColor(R.color.colorGreen)
                .headerLabelColor(android.R.color.white)
                .selectionColor(R.color.colorGreen)
                .todayLabelColor(R.color.colorAccent)
                .dialogButtonsColor(android.R.color.holo_green_dark)
                .previousButtonSrc(R.drawable.ic_chevron_left_black_24dp)
                .forwardButtonSrc(R.drawable.ic_chevron_right_black_24dp);

        DatePicker datePicker = dateBuilder.build();
        datePicker.show();
    }

    //Show Location Theme Dialog
    private void showLocationActivity() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivityForResult(intent, 3000);
        } else {
            Toast.makeText(getApplicationContext(), "스케줄에 위치 추가를 위해 \n GPS 를 켜주세요.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
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

    //Dialog Day Click Event
    @Override
    public void onSelect(List<Calendar> calendars) {
            Toast.makeText(getApplicationContext(), calendars.get(0).getTime().toString(), Toast.LENGTH_LONG).show();
            String strTime  = calendars.get(0).getTime().toString();
            org.joda.time.format.DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern("EEE MMM dd hh:mm:ss ZZZZ yyyy");
            DateTime dt = DateTime.parse(strTime, fmt);
            Log.d(TAG ,"Dt -> " +dt.toString());

    }
}
