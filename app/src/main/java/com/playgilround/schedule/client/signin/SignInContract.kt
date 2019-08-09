package com.playgilround.schedule.client.signin

import android.content.Intent
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

        fun googleSignIn(): Intent

        fun firebaseAuthGoogle(data: Intent)
    }
}