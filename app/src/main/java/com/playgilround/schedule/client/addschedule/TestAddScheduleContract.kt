package com.playgilround.schedule.client.addschedule

import com.playgilround.schedule.client.base.BasePresenter
import com.playgilround.schedule.client.base.BaseView

interface TestAddScheduleContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun onClickNext(position: Int)
    }
}