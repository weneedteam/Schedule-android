package com.playgilround.schedule.client.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendar.listener.OnScheduleScrollListener;
import com.playgilround.schedule.client.calendar.util.ScheduleState;

public class ScheduleLayout extends LinearLayout {

    private CalendarView calendarView;
    private LinearLayout llCalendarView;
    private RelativeLayout rlScheduleList;
    private ScheduleRecyclerView rvScheduleList;
    private LinearLayout llCalendarHeader;

    private GestureDetector mGestureDetector;
    private float mDownPosition[] = new float[2];
    private boolean mIsScrolling = false;

    private int mMinDistance;
    private int mAutoScrollDistance;
    private int mRowSize;
    private ScheduleState mState;

    public ScheduleLayout(Context context) {
        this(context, null);
    }

    public ScheduleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.ScheduleLayout));
        initGestureDetector();
    }

    private void initAttrs(TypedArray array) {
        mState = ScheduleState.OPEN;
        mMinDistance = getResources().getDimensionPixelSize(R.dimen.calendar_min_distance);
        mAutoScrollDistance = getResources().getDimensionPixelSize(R.dimen.auto_scroll_distance);
        mRowSize = getResources().getDimensionPixelSize(R.dimen.week_calendar_height);

    }

    private void initGestureDetector() {
        Log.d(TAG, "onScheduleListener");
        mGestureDetector = new GestureDetector(getContext(), new OnScheduleScrollListener(this));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        resetViewHeight(rlScheduleList, height - mRowSize);
        resetViewHeight(this, height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    static final String TAG = ScheduleLayout.class.getSimpleName();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        llCalendarView = findViewById(R.id.llCalendarView);
        calendarView = findViewById(R.id.calendarView);
        rlScheduleList = findViewById(R.id.rlScheduleList);
        rvScheduleList = findViewById(R.id.rvScheduleList);
        llCalendarHeader = findViewById(R.id.calendarHeader);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onDown...");
                return true;

            case MotionEvent.ACTION_MOVE:
                transferEvent(e);
                Log.d(TAG, "onMove..");
                return true;

            case MotionEvent.ACTION_CANCEL:
                transferEvent(e);
                resetScrollingState();
                return true;
        }
        return super.onTouchEvent(e);
    }

    private void transferEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
    }

    private void resetScrollingState() {
        mDownPosition[0] = 0;
        mDownPosition[1] = 0;
        mIsScrolling = false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownPosition[0] = ev.getRawX();
                mDownPosition[1] = ev.getRawY();
                mGestureDetector.onTouchEvent(ev);
                Log.d(TAG, "dispatch -->" + ev.getRawX() + "--" + ev.getRawY());
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScrolling) {
            return true;
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent");
                float x = ev.getRawX();
                float y = ev.getRawY();

                float distanceX = Math.abs(x - mDownPosition[0]);
                float distanceY = Math.abs(y - mDownPosition[1]);
                if (distanceY > mMinDistance && distanceY > distanceX * 2.0f) {
                    return (y > mDownPosition[1] && isRecyclerViewTouch()) || (y < mDownPosition[1] && mState == ScheduleState.OPEN);
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean isRecyclerViewTouch() {
        return mState == ScheduleState.CLOSE && (rvScheduleList.getChildCount() == 0 ||
            rvScheduleList.isScrollTop());
    }

    public void onCalendarScroll(float distanceY) {
        distanceY = Math.min(distanceY, mAutoScrollDistance);
        float calendarY = llCalendarHeader.getHeight();

        llCalendarView.setY(calendarY);

        float scheduleY = rlScheduleList.getY() - distanceY;

        scheduleY = Math.min(scheduleY, llCalendarView.getHeight() - mRowSize);

        scheduleY = Math.max(scheduleY, calendarY);
        rlScheduleList.setY(scheduleY);
    }

    private void resetViewHeight(View v, int height) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams.height != height) {
            layoutParams.height = height;
            v.setLayoutParams(layoutParams);
        }
    }
}
