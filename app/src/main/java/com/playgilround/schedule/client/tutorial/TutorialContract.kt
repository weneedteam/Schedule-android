package com.playgilround.schedule.client.tutorial

import com.playgilround.schedule.client.base.BasePresenter
import com.playgilround.schedule.client.base.BaseView

/**
 * 19-08-07
 */

interface TutorialContract {
    
    interface View: BaseView<Presenter> {
        fun skipTutorial()
    }

    interface Presenter: BasePresenter {
        fun checkLogin()
    }
}