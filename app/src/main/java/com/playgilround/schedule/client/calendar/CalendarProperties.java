package com.playgilround.schedule.client.calendar;

import android.content.Context;

import com.playgilround.schedule.client.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * 19-03-31
 * all properties of the calendar.
 */
public class CalendarProperties {

    /**
     * 이 캘린더는 현재 기준으로 과거, 미래 +-100년으로 표시됨
     */


    public static final int CALENDAR_SIZE = 2401;
    public static final int FIRST_VISIBLE_PAGE = CALENDAR_SIZE / 2;

    private Context mContext;

    private int mHeaderColor, mHeaderLabelColor, mAbbreviationsBarColor, mAbbreviationsLabelsColor,
            mPagesColor, mDaysLabelsColor, mAnotherMonthsDaysLabelsColor, mCalendarType,
            mDisabledDaysLabelsColor, mSelectionLabelColor, mSelectionColor, mTodayLabelColor, mHeaderVisibility;

    private boolean mEventsEnabled;

    private int mPosition;
    //private DateTime mMinimumDate, mMaximumDate;
    private Calendar mMinimumDate, mMaximumDate;
//    private DateTime mFirstPageCalendarDate = DateUtils.getDateTime();
    private Calendar mFirstPageCalendarDate = DateUtils.getCalendar();

    private int mItemLayoutResource;

    private OnDayClickListener mOnDayClickListener;
    private OnSelectionAbilityListener mOnSelectionAbilityListener;

    private List<EventDay> mEventDays = new ArrayList<>();
    private List<SelectedDay> mSelectedDays = new ArrayList<>();

    public CalendarProperties(Context context) {
        mContext = context;
    }

//    public DateTime getFirstPageDate() {
    public Calendar getFirstPageDate() {
        return mFirstPageCalendarDate;
    }

    public Calendar getMinimumDate() {
        return mMinimumDate;
    }

    public void setMinimumDate(Calendar minimumDate) {
        mMinimumDate = minimumDate;
    }

    public Calendar getMaximumDate() {
        return mMaximumDate;
    }

    public void setMaximumDate(Calendar maximumDate) {
        mMaximumDate = maximumDate;
    }

    public int getItemLayoutResource() {
        return mItemLayoutResource;
    }
    public void setItemLayoutResource(int itemLayoutResource) {
        mItemLayoutResource = itemLayoutResource;
    }

    public int getHeaderColor() {
        if (mHeaderColor <= 0) {
            return mHeaderColor;
        }

        return ContextCompat.getColor(mContext, mHeaderColor);
    }

    public void setHeaderColor(int headerColor) {
        mHeaderColor = headerColor;
    }

    public int getHeaderLabelColor() {
        if (mHeaderLabelColor <= 0) {
            return mHeaderLabelColor;
        }
        return ContextCompat.getColor(mContext, mHeaderLabelColor);
    }

    public void setHeaderLabelColor(int headerLabelColor) {
        mHeaderLabelColor = headerLabelColor;
    }

    public int getHeaderVisibility() {
        return mHeaderVisibility;
    }

    public void setHeaderVisibility(int headerVisibility) {
        mHeaderVisibility = headerVisibility;
    }
    public int getAbbreviationsBarColor() {
        return mAbbreviationsBarColor;
    }

    public void setAbbreviationsBarColor(int abbreviationsBarColor) {
        mAbbreviationsBarColor = abbreviationsBarColor;
    }

    public int getAbbreviationsLabelsColor() {
        return mAbbreviationsLabelsColor;
    }

    public void setAbbreviationsLabelsColor(int abbreviationsLabelsColor) {
        mAbbreviationsLabelsColor = abbreviationsLabelsColor;
    }

    public int getPagesColor() {
        return mPagesColor;
    }

    public void setPagesColor(int pagesColor) {
        mPagesColor = pagesColor;
    }


    public int getDaysLabelsColor() {
        if (mDaysLabelsColor == 0) {
            return ContextCompat.getColor(mContext, R.color.currentMonthDayColor);
        }

        return mDaysLabelsColor;
    }

    public void setDaysLabelsColor(int daysLabelsColor) {
        mDaysLabelsColor = daysLabelsColor;
    }

    public int getAnotherMonthsDaysLabelsColor() {
        if (mAnotherMonthsDaysLabelsColor == 0) {
            return ContextCompat.getColor(mContext, R.color.nextMonthDayColor);
        }

        return mAnotherMonthsDaysLabelsColor;
    }

    public void setAnotherMonthsDaysLabelsColor(int anotherMonthsDaysLabelsColor) {
        mAnotherMonthsDaysLabelsColor = anotherMonthsDaysLabelsColor;
    }

    public int getTodayLabelColor() {
        if (mTodayLabelColor == 0) {
            return ContextCompat.getColor(mContext, R.color.defaultColor);
        }

        return mTodayLabelColor;
    }

    public void setTodayLabelColor(int todayLabelColor) {
        mTodayLabelColor = todayLabelColor;
    }

    public int getSelectionColor() {
        if (mSelectionColor == 0) {
            return ContextCompat.getColor(mContext, R.color.defaultColor);
        }

        return mSelectionColor;
    }

    public void setSelectionColor(int selectionColor) {
        mSelectionColor = selectionColor;
    }

    public int getSelectionLabelColor() {
        if (mSelectionLabelColor == 0) {
            return ContextCompat.getColor(mContext, android.R.color.white);
        }

        return mSelectionLabelColor;
    }

    public void setSelectionLabelColor(int selectionLabelColor) {
        mSelectionLabelColor = selectionLabelColor;
    }

    public int getDisabledDaysLabelsColor() {
        if (mDisabledDaysLabelsColor == 0) {
            return ContextCompat.getColor(mContext, R.color.nextMonthDayColor);
        }

        return mDisabledDaysLabelsColor;
    }

    public void setDisabledDaysLabelsColor(int disabledDaysLabelsColor) {
        mDisabledDaysLabelsColor = disabledDaysLabelsColor;
    }

    public int getCalendarType() {
        return mCalendarType;
    }

    public void setCalendarType(int calendarType) {
        mCalendarType = calendarType;
    }

    public boolean getEventsEnabled() {
        return mEventsEnabled;
    }

    public void setEventsEnabled(boolean eventsEnabled) {
        mEventsEnabled = eventsEnabled;
    }

    public int getCalendarPosition() {
        return mPosition;
    }

    public void setCalendarPosition(int position) {
        mPosition = position;
    }

    public OnDayClickListener getOnDayClickListener() {
        return mOnDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public List<EventDay> getEventDays() {
        return mEventDays;
    }

    public void setEventDays(List<EventDay> eventDays) {
        mEventDays = eventDays;
    }

    public List<SelectedDay> getSelectedDays() {
        return mSelectedDays;
    }

    public void setSelectedDay(SelectedDay selectedDay) {
        mSelectedDays.clear();
        mSelectedDays.add(selectedDay);
    }

    public OnSelectionAbilityListener getOnSelectionAbilityListener() {
        return mOnSelectionAbilityListener;
    }

    public void setOnSelectionAbilityListener(OnSelectionAbilityListener onSelectionAbilityListener) {
        mOnSelectionAbilityListener = onSelectionAbilityListener;
    }
}
