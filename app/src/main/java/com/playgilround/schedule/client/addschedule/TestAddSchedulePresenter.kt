package com.playgilround.schedule.client.addschedule

import android.content.Context
import com.playgilround.schedule.client.ScheduleApplication
import javax.inject.Inject

class TestAddSchedulePresenter constructor(mContext: Context, private val mView: TestAddScheduleContract.View): TestAddScheduleContract.Presenter {

    init {
        mView.setPresenter(this)
        (mContext.applicationContext as ScheduleApplication).appComponent.addScheduleInject(this)
    }

    override fun start() {

    }
}