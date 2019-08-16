package com.playgilround.schedule.client.addschedule

import android.content.Context
import com.playgilround.schedule.client.ScheduleApplication
import com.playgilround.schedule.client.addschedule.model.ScheduleDataModel
import com.playgilround.schedule.client.addschedule.view.AddScheduleAdapter
import com.playgilround.schedule.client.data.ScheduleData
import javax.inject.Inject

class TestAddSchedulePresenter constructor(mContext: Context?, private val mView: TestAddScheduleContract.View, private val mScheduleDataModel: ScheduleDataModel): TestAddScheduleContract.Presenter {

    @Inject
    internal lateinit var mSchedule: ScheduleData

    init {
        mView.setPresenter(this)
        (mContext?.applicationContext as ScheduleApplication).appComponent.addScheduleInject(this)
    }

    override fun start() {

    }

    override fun onClickNext(position: Int) {
        var check = false

        when (position) {
            AddScheduleAdapter.TYPE_SCHEDULE_TITLE -> {
                mSchedule.title = mScheduleDataModel.getScheduleTitle()
                check = mSchedule.title != null
            }
            AddScheduleAdapter.TYPE_SCHEDULE_DATE -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MEMBER -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_PLACE -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MEMO -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MAP -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_RESULT -> {}
        }
        mView.fieldCheck(check)
    }
}