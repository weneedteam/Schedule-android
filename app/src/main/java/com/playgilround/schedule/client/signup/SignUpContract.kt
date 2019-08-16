package com.playgilround.schedule.client.signup

import com.playgilround.schedule.client.base.BasePresenter
import com.playgilround.schedule.client.base.BaseView

interface SignUpContract {

    interface View: BaseView<Presenter> {
        fun fieldCheck(check: Boolean)

        fun signUpError(status: Int)

        fun signUpComplete()
    }

    interface Presenter: BasePresenter {
        fun onClickNext(position: Int)

        fun onClickBack(position: Int)

        fun validateEmail(email: String): Boolean

        fun validateNickName(nickName: String): Boolean

        fun signUp()
    }
}