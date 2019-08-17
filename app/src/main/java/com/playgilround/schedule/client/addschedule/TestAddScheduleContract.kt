package com.playgilround.schedule.client.addschedule

import com.playgilround.schedule.client.base.BasePresenter
import com.playgilround.schedule.client.base.BaseView

interface TestAddScheduleContract {

    interface View: BaseView<Presenter> {
        fun fieldCheck(check: Boolean)
    }

    interface Presenter: BasePresenter {
        fun onClickNext(position: Int)

        fun onClickBack(position: Int)

        fun scheduleSave()

        fun getFriendList()

        fun rxUnSubscribe()
    }
}