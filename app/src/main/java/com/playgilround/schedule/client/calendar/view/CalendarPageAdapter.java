package com.playgilround.schedule.client.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendar.CalendarGridView;
import com.playgilround.schedule.client.calendar.CalendarProperties;
import com.playgilround.schedule.client.calendar.listener.DayRowClickListener;
import com.playgilround.schedule.client.calendar.util.SelectedDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static com.playgilround.schedule.client.calendar.CalendarProperties.CALENDAR_SIZE;


/**
 * 달력 페이지 로드 Adapter
 */
public class CalendarPageAdapter extends PagerAdapter {

    private static final String TAG = CalendarPageAdapter.class.getSimpleName();

    private Context mContext;

    private CalendarProperties mCalendarProperties;
    private CalendarGridView mCalendarGridView;

    private int mPageMonth;

    public CalendarPageAdapter(Context context, CalendarProperties calendarProperties) {
        mContext = context;
        mCalendarProperties = calendarProperties;
        inforDatePicker();
    }

    @Override
    public int getCount() {
        return CALENDAR_SIZE;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCalendarGridView = (CalendarGridView) inflater.inflate(R.layout.calendar_view_grid, null);

        loadMonth(position);

        mCalendarGridView.setOnItemClickListener(new DayRowClickListener(this,
                mCalendarProperties, mPageMonth));
        container.addView(mCalendarGridView);
        return mCalendarGridView;
    }

    public List<SelectedDay> getSelectedDays() {
        return mCalendarProperties.getSelectedDays();
    }

    Calendar getFirstSelectedDay() {
        int size = mCalendarProperties.getSelectedDays().size();
        Calendar firstCal = mCalendarProperties.getSelectedDays().get(0).getCalendar();
        Calendar secondCal = mCalendarProperties.getSelectedDays().get(size - 1).getCalendar();

        if (size == 1) {
            return null;
        } else {
            if (firstCal.before(secondCal)) {
                return firstCal;
            } else {
                return secondCal;
            }
        }
    }

    Calendar getLastSelectedDay() {
        int size = mCalendarProperties.getSelectedDays().size();
        Calendar firstCal = mCalendarProperties.getSelectedDays().get(0).getCalendar();
        Calendar secondCal = mCalendarProperties.getSelectedDays().get(size - 1).getCalendar();

        if (size == 1) {
            return null;
        } else {
            if (firstCal.before(secondCal)) {
                return secondCal;
            } else {
                return firstCal;

            }
        }
    }

    public SelectedDay getSelectedDay() {
        return mCalendarProperties.getSelectedDays().get(0);
    }


    public void setSelectedDay(SelectedDay selectedDay) {
        mCalendarProperties.setSelectedDay(selectedDay);
        inforDatePicker();
    }

    public void addSelectedDay(SelectedDay selectedDay) {
        if (!mCalendarProperties.getSelectedDays().contains(selectedDay)) {
            mCalendarProperties.getSelectedDays().add(selectedDay);
            inforDatePicker();
            return;
        }
    }

    private void inforDatePicker() {
        if (mCalendarProperties.getOnSelectionAbilityListener() != null) {
            mCalendarProperties.getOnSelectionAbilityListener().onChange(mCalendarProperties.getSelectedDays().size() > 0);
        }
    }


    //GridView에 일 추가.
    private void loadMonth(int position) {
        ArrayList<Date> days = new ArrayList<>();
        ArrayList<Date> curDays = new ArrayList<>();

        Calendar calendar = (Calendar) mCalendarProperties.getFirstPageDate().clone();

        //오늘 날짜 얻기
        calendar.add(Calendar.MONTH, position);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        //Calendar 날짜가 무슨 요일인지?
        //현재 달, 저번 달, 다음 달 기준
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        Log.d(TAG, "dayOfWeek ->" + dayOfWeek);

        //한 주의 시작이 무슨 요일 인가.
        int firstDayOfWeek = calendar.getFirstDayOfWeek();

        int monthBeginningCell = (dayOfWeek < firstDayOfWeek ? 7 : 0) + dayOfWeek - firstDayOfWeek;

//        Log.d(TAG, "monthBeginning ->" + monthBeginningCell);

        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
/*
        Calendar calendar2 = mCalendarProperties.getFirstPageDate();
        calendar2.floating_add(Calendar.MONTH, position);
        calendar2.set(Calendar.DAY_OF_MONTH, 1);*/

//        Log.d(TAG, "set calendar2 ->" + calendar2.getTime());
        int retPosition = mCalendarProperties.getCalendarPosition();
        while (days.size() < 42) {
//            Calendar retCalendar =  ((Calendar) mCalendarProperties.getFirstPageDate().clone());
//            retCalendar.floating_add(Calendar.MONTH, retPosition);
//            Log.d(TAG ,"Month Check ->" + retCalendar.get(Calendar.MONTH));
//            Log.d(TAG, "view days ->" + calendar.get(Calendar.MONTH));
//
            days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            if (retCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
//                Log.d(TAG, "ret Days check ->" + calendar.getTime());
//                curDays.floating_add(calendar.getTime());
//                calendar.floating_add(Calendar.DAY_OF_MONTH, 1);
//            } else {
//                Log.d(TAG, "ret Days check ->" + calendar.getTime());
//
//            }
        }

        mPageMonth = calendar.get(Calendar.MONTH) - 1;
        CalendarDayAdapter calendarDayAdapter = new CalendarDayAdapter(this, mContext,
                mCalendarProperties, days, mPageMonth, position);

        mCalendarGridView.setAdapter(calendarDayAdapter);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
