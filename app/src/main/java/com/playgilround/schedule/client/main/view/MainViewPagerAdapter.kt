package com.playgilround.schedule.client.main.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainViewPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var mFragmentList = ArrayList<Fragment>()
    var mImageList = ArrayList<Int>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(drawable: Int, fragment: Fragment) {
        mImageList.add(drawable)
        mFragmentList.add(fragment)
    }
}