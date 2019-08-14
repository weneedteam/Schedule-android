package com.playgilround.schedule.client.addschedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.addschedule.view.AddScheduleAdapter
import com.playgilround.schedule.client.base.BaseFragment
import kotlinx.android.synthetic.main.add_schedule_frag.*

class TestAddScheduleFragment: BaseFragment(), TestAddScheduleContract.View {

    private lateinit var mPresenter: TestAddScheduleContract.Presenter
    private lateinit var mAdapter: AddScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = AddScheduleAdapter(context)
        return  inflater.inflate(R.layout.add_schedule_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_add_schedule.setHasFixedSize(true)
        recycler_add_schedule.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_add_schedule.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        setOnClickListener()
    }

    fun setOnClickListener() {
        schedule_next_btn.setOnClickListener {
            Log.d("TEST", "Position --${mAdapter.position}")
            mPresenter.onClickNext(mAdapter.position)
        }
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