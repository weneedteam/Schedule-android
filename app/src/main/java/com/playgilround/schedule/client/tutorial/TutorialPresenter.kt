package com.playgilround.schedule.client.tutorial

import android.content.Context
import com.playgilround.schedule.client.ScheduleApplication
import com.playgilround.schedule.client.data.repository.UsersRepository
import javax.inject.Inject

/**
 * 19-08-07 cho
 */
class TutorialPresenter constructor(private val mContext: Context, private val mView: TutorialContract.View): TutorialContract.Presenter {

    @Inject
    internal lateinit var mUsersRepository: UsersRepository

    init {
        mView.setPresenter(this)
        (mContext.applicationContext as ScheduleApplication).appComponent.tutorialInject(this)
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkLogin() {
        if (mUsersRepository.getCurrentUser(mContext) != null) mView.skipTutorial()
    }
}