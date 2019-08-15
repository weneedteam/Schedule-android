package com.playgilround.schedule.client.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.CallbackManager
import com.nhn.android.naverlogin.OAuthLogin
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
        (mContext.applicationContext as ScheduleApplication).appComponent.signInInject(this)

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

    override fun facebookSignIn(activity: Activity): CallbackManager {
        return mRepository.facebookLogin(activity, this)
    }

    override fun naverInit(): OAuthLogin {
        return mRepository.naverInit()
    }

    override fun naverSignIn(activity: Activity, oAuthLogin: OAuthLogin) {
        mRepository.naverLogin(activity, oAuthLogin, this)
    }

    override fun kakaoSignIn(activity: Activity) {
        mRepository.kakaoLogin(activity, this)
    }

    override fun googleSignIn(): Intent {
        return mRepository.googleLogin()
    }

    override fun firebaseAuthGoogle(data: Intent) {
        mRepository.firebaseAuthGoogle(data, this)
    }

    override fun onUserLoaded() {
        mView.signInComplete()
    }

    override fun onDataNotAvailable(error: Int) {
        mView.signInError(error)
    }

    companion object {
        const val ERROR_EMAIL = 0x0001
        const val ERROR_PASSWORD = 0x0002
        const val ERROR_FAIL_SIGN_IN = 0x0003
        const val ERROR_NETWORK_CUSTOM = 0x0004
    }
}