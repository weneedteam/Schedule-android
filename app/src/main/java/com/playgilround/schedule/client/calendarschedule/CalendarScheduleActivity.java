package com.playgilround.schedule.client.calendarschedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendar.CalendarView;
import com.playgilround.schedule.client.calendar.EventDay;
import com.playgilround.schedule.client.infoschedule.InfoScheduleActivity;
import com.playgilround.schedule.client.manyschedule.ManyScheduleActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.annimon.stream.Stream.of;
import static com.playgilround.schedule.client.infoschedule.InfoScheduleActivity.ADD_SCHEDULE;

/**
 * 18-12-26
 * Schedule Calendar Activity
 * added by CHO
 * Test
 */
public class CalendarScheduleActivity extends AppCompatActivity implements CalendarScheduleContract.View {

    static final String TAG = CalendarScheduleActivity.class.getSimpleName();

    @BindView(R.id.calendarView)
    CalendarView calendarView;

//    @BindView(R.id.ivAddSchedule)
//    ImageView ivAddSchedule;

    private CalendarScheduleContract.Presenter mPresenter;

    private long[] mNotes = new long[2]; //back button save.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);

        new CalendarSchedulePresenter(this, this);

     /*   // 날짜 클릭 시 다이얼로그
        calendarView.setOnDayClickListener(eventDay -> {
            // 유저가 클릭한 날짜와, 현재 클릭되어있는 날짜가 같을 경우에만 Dialog 표시
            String dateString = mPresenter.convertCalendarToDateString(eventDay);
            if (dateString == null) {
                // Todo:: Error message
                Log.e(TAG, "Convert Error");
            } else if (!dateString.isEmpty()) {
                showCalendarDialog(dateString);
            }
        });

        // 다음 달로 이동
        calendarView.setOnForwardPageChangeListener(this::callSchedules);

        // 전 달로 이동
        calendarView.setOnPreviousPageChangeListener(this::callSchedules);

        callSchedules();*/

  /*      ivAddSchedule.setOnClickListener(v -> {
            ArrayList arrManyDays = mPresenter.getSelectedManyDays(calendarView.getSelectedDates());
            String strInput = etInput.getText().toString();
            if (arrManyDays == null) {
                // Todo:: Error message
                Toast.makeText(getApplicationContext(), "날짜를 지정해주세요.", Toast.LENGTH_LONG).show();
            } else if (strInput.equals("")) {
                Toast.makeText(getApplicationContext(), "스케줄을 적어주세요.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, ManyScheduleActivity.class);
                intent.putExtra("manyDate", arrManyDays);
                intent.putExtra("inputText", strInput);
                startActivityForResult(intent, ADD_SCHEDULE);
            }
        });

        ivRange.setOnClickListener(v -> openRangePicker());*/
    }

    private void openRangePicker() {

        Calendar today = Calendar.getInstance();

        List<Calendar> selectDays = new ArrayList<>();
        selectDays.add(today);
/*
        DatePickerBuilder rangeBuilder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.RANGE_PICKER)
                .headerColor(R.color.colorGreen)
                .abbreviationsBarColor(R.color.light_indigo)
                .abbreviationsLabelsColor(android.R.color.white)
                .pagesColor(R.color.pages_color)
                .selectionColor(android.R.color.white)
                .selectionLabelColor(R.color.color_pink)
                .todayLabelColor(R.color.colorAccent)
                .dialogButtonsColor(android.R.color.white)
                .anotherMonthsDaysLabelsColor(R.color.pages_color)
                .daysLabelsColor(android.R.color.white)
                .selectedDays(selectDays);

        DatePicker rangePicker = rangeBuilder.build();
        rangePicker.show();*/
    }

  /*  @Override
    public void onSelect(List<Calendar> calendars) {
        of(calendars).forEach(calendar ->
                Toast.makeText(getApplicationContext(),
                        calendar.getTime().toString(),
                        Toast.LENGTH_SHORT).show());

//        calendarView.setSelectedDates(calendars);
    }
*/
    // yyyy-MM 기준으로 저장된 스케줄 표시
    private void callSchedules() {
//        mPresenter.getSchedule(calendarView.getCurrentPageDate().getTimeInMillis());
    }

    //show Dialog When User Click Calendar
    public void showCalendarDialog(String dateString) {
        // https://hashcode.co.kr/questions/3073/mvp-패턴에서-startactivity는-어디서-해야하나요
        Intent intent = new Intent(this, InfoScheduleActivity.class);
        intent.putExtra("date", dateString);
        startActivityForResult(intent, ADD_SCHEDULE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_SCHEDULE:
                // 스케줄 입력이 완료됬을 때
                callSchedules();
//                calendarView.setSelectedDates(new ArrayList<>());
                break;
        }
    }

    //Navigation
    @Override
    public void onBackPressed() {
        //http://forum.falinux.com/zbxe/index.php?document_srl=571358&mid=lecture_tip
        System.arraycopy(mNotes, 1, mNotes, 0, mNotes.length - 1);
        mNotes[mNotes.length - 1] = SystemClock.uptimeMillis();
        if (SystemClock.uptimeMillis() - mNotes[0] < 1000) {
            finish();
        } else {
            Toast.makeText(this, getString(R.string.text_exit_app), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    //실제 View 가 만들어지는 시점
    @Override
    public void setPresenter(CalendarScheduleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.realmClose();
    }

    @Override
    public void addEvents(List<EventDay> events) {
//        calendarView.setEvents(events);
    }

    @Override
    public Drawable getOneIcon(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_one_icons);
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    @Override
    public Drawable getTwoIcon(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_two_icons);
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    @Override
    public Drawable getThreeIcon(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_three_icons);
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    @Override
    public Drawable getFourIcon(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_four_icons);
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }
}
