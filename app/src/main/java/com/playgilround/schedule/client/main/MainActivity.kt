package com.playgilround.schedule.client.main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.calendarschedule.CalendarScheduleFragment
import com.playgilround.schedule.client.chat.ChatFragment
import com.playgilround.schedule.client.friend.FriendFragment
import com.playgilround.schedule.client.main.view.MainViewPagerAdapter
import com.playgilround.schedule.client.map.MapFragment
import com.playgilround.schedule.client.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter

    private val drawableList = arrayOf(
            R.drawable.image_nav_bar_friend,
            R.drawable.image_nav_bar_chat,
            R.drawable.image_nav_bar_calendar,
            R.drawable.image_nav_bar_map,
            R.drawable.image_nav_bar_setting
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainPresenter(this)

        val pagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(drawableList[0], FriendFragment())
        pagerAdapter.addFragment(drawableList[1], ChatFragment())
        pagerAdapter.addFragment(drawableList[2], CalendarScheduleFragment())
        pagerAdapter.addFragment(drawableList[3], MapFragment())
        pagerAdapter.addFragment(drawableList[4], SettingFragment())

        view_pager_main.adapter = pagerAdapter

        nav_main.setupWithViewPager(view_pager_main)

        for (i in 0 until view_pager_main.adapter!!.count) {
            nav_main.getTabAt(i)!!.setIcon(pagerAdapter.mImageList[i])
        }

    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }
}