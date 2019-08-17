package com.playgilround.schedule.client.addschedule

import android.content.Context
import com.playgilround.schedule.client.ScheduleApplication
import com.playgilround.schedule.client.addschedule.model.ScheduleDataModel
import com.playgilround.schedule.client.addschedule.view.AddScheduleAdapter
import com.playgilround.schedule.client.data.ScheduleData
import com.playgilround.schedule.client.data.repository.FriendRepository
import com.playgilround.schedule.client.model.ResponseMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TestAddSchedulePresenter constructor(mContext: Context?, private val mView: TestAddScheduleContract.View, private val mScheduleDataModel: ScheduleDataModel): TestAddScheduleContract.Presenter {

    @Inject
    internal lateinit var mSchedule: ScheduleData

    @Inject
    internal lateinit var mFriendRepository: FriendRepository

    private var mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        (mContext?.applicationContext as ScheduleApplication).appComponent.addScheduleInject(this)
        mCompositeDisposable = CompositeDisposable()
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
            AddScheduleAdapter.TYPE_SCHEDULE_DATE -> {
                //TEST
                mSchedule.startDate = "2019-06-22T17:30:00"
                mSchedule.endDate = "2019-06-22T17:30:00"
                check = true
            }
            AddScheduleAdapter.TYPE_SCHEDULE_MEMBER -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_PLACE -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MEMO -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_MAP -> {}
            AddScheduleAdapter.TYPE_SCHEDULE_RESULT -> {}
        }
        mView.fieldCheck(check)
    }

    override fun onClickBack(position: Int) {
        when (position) {
            AddScheduleAdapter.TYPE_SCHEDULE_TITLE -> mSchedule.title = null
            AddScheduleAdapter.TYPE_SCHEDULE_DATE -> {
                mSchedule.startDate = null
                mSchedule.endDate = null
            }
            AddScheduleAdapter.TYPE_SCHEDULE_MEMBER -> mSchedule.member = null
            AddScheduleAdapter.TYPE_SCHEDULE_PLACE -> mSchedule.place = null
            AddScheduleAdapter.TYPE_SCHEDULE_MEMO -> mSchedule.memo = null
            AddScheduleAdapter.TYPE_SCHEDULE_MAP -> mSchedule.map = null
            AddScheduleAdapter.TYPE_SCHEDULE_RESULT -> mSchedule.result = null
        }
    }

    override fun scheduleSave() {

    }

    override fun getFriendList() {
        val disposable = mFriendRepository.getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(DisposableSingleObserver<ResponseMessage>()) {
            }
    }

    override fun rxUnSubscribe() {
     if (null != mCompositeDisposable) mCompositeDisposable.clear()
    }
}