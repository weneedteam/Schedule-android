package com.playgilround.schedule.client.signin

import android.app.Activity
import android.content.Intent
import com.facebook.CallbackManager
import com.nhn.android.naverlogin.OAuthLogin
import com.playgilround.schedule.client.base.BasePresenter
import com.playgilround.schedule.client.base.BaseView

interface SignInContract {

    interface View: BaseView<Presenter> {

        fun showProgressBar()

        fun hideProgressBar()

        fun signInComplete()

        fun signInError(status: Int)
    }

    interface Presenter: BasePresenter {

        fun checkAutoSignIn(): Boolean

        fun autoSignIn()

        fun signIn(email: String, password: String)

        fun facebookSignIn(activity: Activity): CallbackManager

        fun naverInit(): OAuthLogin

        fun naverSignIn(activity: Activity, oAuthLogin: OAuthLogin)

        fun googleSignIn(): Intent

        fun firebaseAuthGoogle(data: Intent)
    }
}