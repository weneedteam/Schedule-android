package com.playgilround.schedule.client.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class BaseFragment: Fragment() {

    fun getViewFragmentManager(): FragmentManager? {
        return fragmentManager
    }
}