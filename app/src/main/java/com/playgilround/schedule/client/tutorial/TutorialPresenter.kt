package com.playgilround.schedule.client.tutorial

import android.content.Context
import com.playgilround.schedule.client.data.repository.UsersRepository

/**
 * 19-08-07 cho
 */
class TutorialPresenter constructor(private val mContext: Context, private val mView: TutorialContract.View, private val mUsersRepository: UsersRepository): TutorialContract.Presenter {

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkLogin() {
        if (mUsersRepository.getCurrentUser(mContext) != null) mView.skipTutorial()
    }
}