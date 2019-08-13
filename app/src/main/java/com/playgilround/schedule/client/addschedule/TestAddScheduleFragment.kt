package com.playgilround.schedule.client.addschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.base.BaseFragment

class TestAddScheduleFragment: BaseFragment(), TestAddScheduleContract.View {

    private lateinit var mPresenter: TestAddScheduleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_schedule, container, false)
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