package com.playgilround.schedule.client.signin

import android.content.Context
import android.content.Intent
import com.playgilround.schedule.client.ScheduleApplication
import com.playgilround.schedule.client.data.repository.UsersRepository
import com.playgilround.schedule.client.data.source.UsersDataSource
import javax.inject.Inject

class SignInPresenter constructor(private val mContext: Context, private val mView: SignInContract.View):
        SignInContract.Presenter, UsersDataSource.LoginCallBack {

    @Inject
    internal lateinit var mRepository: UsersRepository

    init {
        mView.setPresenter(this)
        (mContext.applicationContext as ScheduleApplication).appComponent.inject(this)

    }

    override fun start() {

    }

    override fun checkAutoSignIn(): Boolean {
        return mRepository.getCurrentUser(mContext) != null
    }

    override fun autoSignIn() {
        mView.showProgressBar()
        mRepository.tokenLogin(this)
    }

    override fun signIn(email: String, password: String) {
        mView.showProgressBar()
        mRepository.login(email, password, this)
    }

    override fun googleSignIn(): Intent {
        return mRepository.googleLogin()
    }

    override fun firebaseAuthGoogle(data: Intent) {
        mRepository.firebaseAuthGoogle(data)
    }

    override fun onUserLoaded() {
        mView.signInComplete()
    }

    override fun onDataNotAvailable(error: Int) {
        mView.signInError(error)
    }
}