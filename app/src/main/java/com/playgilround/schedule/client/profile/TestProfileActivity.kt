package com.playgilround.schedule.client.profile

import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.playgilround.schedule.client.R
import kotlinx.android.synthetic.main.activity_profile.*

class TestProfileActivity: AppCompatActivity() {

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.floating_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.floating_close)

        setOnClickListener()
    }

    fun setOnClickListener() {
        floatingBtn.setOnClickListener {
            isFabOpen = if (isFabOpen) {
                floatingBtn.setImageResource(R.drawable.floating_add)
                llAttention.startAnimation(fabClose)
                llDeleteFriend.startAnimation(fabClose)
                false
            } else {
                floatingBtn.setImageResource(R.drawable.floating_exit)
                llAttention.startAnimation(fabOpen)
                llDeleteFriend.startAnimation(fabOpen)
                true
            }
        }
    }
}