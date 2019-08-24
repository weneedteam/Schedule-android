/*
package com.playgilround.schedule.client.profile;


import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.playgilround.schedule.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

*/
/**
 * 19-03-15
 * 나(친구) 프로필 관련 Fragment
 *//*

public class ProfileActivity extends Activity {

    @BindView(R.id.fabTop)
    FloatingActionButton fabTop;

    @BindView(R.id.fabMiddle)
    FloatingActionButton fabMiddle;

    @BindView(R.id.fabBottom)
    FloatingActionButton fabBottom;

    private Animation animOpen, animClose;

    private Boolean isFabOpen = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        animOpen = AnimationUtils.loadAnimation(this, R.anim.floating_open);
        animClose = AnimationUtils.loadAnimation(this, R.anim.floating_close);

        fabBottom.setOnClickListener(l -> floatingAnim());
        fabMiddle.setOnClickListener(l -> floatingAnim());
        fabTop.setOnClickListener(l -> floatingAnim());
    }

    public void floatingAnim() {
       if (isFabOpen) {
           fabTop.startAnimation(animClose);
           fabMiddle.startAnimation(animClose);

           fabTop.setClickable(false);
           fabMiddle.setClickable(false);
           isFabOpen = false;
       } else {
           fabTop.startAnimation(animOpen);
           fabMiddle.startAnimation(animOpen);

           fabTop.setClickable(true);
           fabMiddle.setClickable(true);
           isFabOpen = true;
       }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
*/
