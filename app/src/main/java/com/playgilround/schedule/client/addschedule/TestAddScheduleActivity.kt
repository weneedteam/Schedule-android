package com.playgilround.schedule.client.addschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.util.ActivityUtils

class TestAddScheduleActivity: AppCompatActivity() {

    lateinit var mPresenter: TestAddScheduleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        var testAddScheduleFragment = supportFragmentManager.findFragmentById(R.id.add_schedule_frag) as TestAddScheduleFragment

        if (null == testAddScheduleFragment) {
            testAddScheduleFragment = TestAddScheduleFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, testAddScheduleFragment, R.id.add_schedule_frag)
        }
    }
}