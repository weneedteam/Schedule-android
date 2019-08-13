package com.playgilround.schedule.client.addschedule

import android.os.Bundle
import com.playgilround.schedule.client.base.BaseFragment

class TestAddScheduleFragment: BaseFragment(), TestAddScheduleContract.View {

    private lateinit var mPresenter: TestAddScheduleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun setPresenter(presenter: TestAddScheduleContract.Presenter) {
        mPresenter = presenter
    }


    companion object {
        fun newInstance(): TestAddScheduleFragment {
            return TestAddScheduleFragment()
        }
    }
}