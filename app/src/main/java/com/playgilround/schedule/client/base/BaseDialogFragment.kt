package com.playgilround.schedule.client.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.lang.IllegalStateException

open class BaseDialogFragment: DialogFragment() {

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commit()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }
}