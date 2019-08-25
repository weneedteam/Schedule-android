package com.playgilround.schedule.client.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.provider.MediaStore.Audio.AudioColumns.TITLE_KEY
import android.util.Log
import android.view.View
import android.widget.TextView
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_one_line_title_two_button.*

class FriendDeleteDialogFragment: BaseDialogFragment() {

    private var mTitle: String? = null
    private var mContent: String? = null

    private lateinit var mTvTitle: TextView
    private lateinit var mTvContent: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        if (null != arguments) {

            mTitle = arguments!!.getString(TITLE_KEY)
            mContent = arguments!!.getString(CONTENT_KEY)
            Log.d("TEST", "argument$mTitle -- $mContent")
        }

        val builder = AlertDialog.Builder(activity, R.style.DefaultDialogTheme)
        val inflater = activity!!.layoutInflater

        val rootView = inflater.inflate(R.layout.dialog_one_line_title_two_button, null)

        //DialogFragment onViewCreated 동작 x (DataBinding 적용 예정)
//        tvTitle.rootView.findViewById<TextView>(R.id.tvTitle).text = mTitle
//        tvContent.rootView.findViewById<TextView>(R.id.tvContent).text = mContent

        mTvTitle = rootView.findViewById(R.id.tvTitle)
        mTvContent = rootView.findViewById(R.id.tvContent)

        mTvTitle.text = mTitle
        mTvContent.text = mContent
        builder.setView(rootView)

        return builder.create()
    }

    companion object {
       const val CONTENT_KEY = "CONTENT"
       const val TITLE_KEY = "TITLE"
    }
}