package com.playgilround.schedule.client.addschedule

import android.content.Context
import com.playgilround.schedule.client.ScheduleApplication
import com.playgilround.schedule.client.addschedule.view.AddScheduleAdapter
import javax.inject.Inject

class TestAddSchedulePresenter constructor(mContext: Context, private val mView: TestAddScheduleContract.View): TestAddScheduleContract.Presenter {

    init {
        mView.setPresenter(this)
        (mContext.applicationContext as ScheduleApplication).appComponent.addScheduleInject(this)
    }

    override fun start() {

    }

    override fun onClickNext(position: Int) {
        var check = false

        when (position) {
            AddScheduleAdapter.TYPE_SCHEDULE_TITLE -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_DATE -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MEMBER -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_PLACE -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MEMO -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MAP -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_RESULT -> {}
        }
    }
}