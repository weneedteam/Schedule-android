package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.Schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

/**
 * 18-12-30
 * 스케줄 추가 관련 Activity
 */
public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener, OnSelectDateListener, OnDateSetListener {

    static final String TAG = AddScheduleActivity.class.getSimpleName();

    TextView tvDate, tvTime, tvLocation;
    Button btnConfirm;
    Realm realm;

    EditText etTitle, etDesc;
    String strDay;

    //SetLocationActivity.class 에서 받은 위치정보.
    String resLocation;
    Double resLatitude;
    Double resLongitude;

    public static final int LOCATION_START = 0x1000;
    public static final int LOCATION_OK = 0x1001;
    public static final String HOUR_MINUTE = "hour_minute";

    TimePickerDialog timePickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_add_schedule);

        realm = Realm.getDefaultInstance();

        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tvScheduleTime);
        tvLocation = findViewById(R.id.tvScheduleLocation);

        btnConfirm = findViewById(R.id.btn_confirm);
        etTitle = findViewById(R.id.etScheduleTitle);
        etDesc = findViewById(R.id.etScheduleDesc);

        findViewById(R.id.llScheduleTime).setOnClickListener(this);
        findViewById(R.id.llScheduleLocation).setOnClickListener(this);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        String strYear = date.substring(0, 4);
        String strMonth = date.substring(4, 6);
        String strDay = date.substring(6, 8);

        String strDate = strYear + "년 " + strMonth + "월 " + strDay + "일";

        /*try {
            String strTime  = strYear + strMonth + strDay;
            Date date2 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(strTime);
            long milliseconds = date2.getTime();
            Log.d(TAG, "dt result -> " + milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //Get Current Time
//        dateTime = new DateTime();
//        String curTime = dateTime.toString("HH:mm");

        String strTime = strYear + "-" + strMonth + "-" + strDay + " " + "00:00";
        tvDate.setText(strDate);
        tvTime.setText(strTime);

        btnConfirm.setOnClickListener(l -> {
            confirm();
        });

        //TimePicker
        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setCallBack(this)
                .setSureStringId(getString(R.string.button_confirm))
                .setCancelStringId(getString(R.string.button_cancel))
                .setTitleStringId(getString(R.string.text_time_set))
                .setThemeColor(getResources().getColor(R.color.colorGreen))
                .setHourText(getString(R.string.text_hour))
                .setMinuteText(getString(R.string.text_minute))
                .setWheelItemTextSize(20)
                .build();
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
            Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_input_schedule), Toast.LENGTH_LONG).show();
        } else {
            realm.executeTransaction(realm -> {
                Number currentIdNum = realm.where(Schedule.class).max("id");

                int nextId;

                if (currentIdNum == null) {
                    nextId = 0;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }

                Schedule mSchedule = realm.createObject(Schedule.class, nextId);
                mSchedule.setTitle(etTitle.getText().toString());
//                mSchedule.setTitle();
                mSchedule.setLocation(resLocation);
                mSchedule.setLatitude(resLatitude);
                mSchedule.setLongitude(resLongitude);
                mSchedule.setDesc(etDesc.getText().toString());
                Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_save_schedule), Toast.LENGTH_LONG).show();
                finish();
            });

        }
    }

    //Show Select Calendar Dialog
    private void showCalendarDialog() {
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
            startActivityForResult(intent, LOCATION_START);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_gps_enable), Toast.LENGTH_LONG).show();
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
        try {
            String strSelect = calendars.get(0).getTime().toString();
            Date date = new SimpleDateFormat(getString(R.string.text_date_all_format), Locale.ENGLISH).parse(strSelect);
            long milliseconds = date.getTime();

            DateTime dateTime = new DateTime(Long.valueOf(milliseconds), DateTimeZone.UTC);
            strDay = dateTime.toString(getString(R.string.text_date_day));

            tvTime.setText(strDay); //변경한 날짜로 재 표시

            //시, 분을 설정하는 다이얼로그 표시
            timePickerDialog.show(getSupportFragmentManager(), HOUR_MINUTE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Dialog Time Click Event
    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long milliseconds) {

        DateTime dateTime = new DateTime(Long.valueOf(milliseconds), DateTimeZone.UTC);
        String strTime = dateTime.toString(getString(R.string.text_date_time));

        String strDayTime = strDay + " " + strTime;
        tvTime.setText(strDayTime);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == LOCATION_OK) {
            switch (requestCode) {
                case LOCATION_START:

                    resLocation = data.getStringExtra(SetLocationActivity.INTENT_EXTRA_LOCATION);
                    resLatitude = data.getDoubleExtra(SetLocationActivity.INTENT_EXTRA_LATITUDE, 0);
                    resLongitude = data.getDoubleExtra(SetLocationActivity.INTENT_EXTRA_LONGITUDE, 0);

                    if (TextUtils.isEmpty(resLocation)) {
                        tvLocation.setText(R.string.text_add_location);
                    } else {
                        tvLocation.setText(resLocation);
                    }
                    break;
            }
        }
    }
}
