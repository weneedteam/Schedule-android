package com.playgilround.schedule.client.profile;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.playgilround.schedule.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-03-15
 * 나(친구) 프로필 관련 Fragment
 */
public class ProfileFragment extends DialogFragment {

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @BindView(R.id.fabTop)
    FloatingActionButton fabTop;

    @BindView(R.id.fabMiddle)
    FloatingActionButton fabMiddle;

    @BindView(R.id.fabBottom)
    FloatingActionButton fabBottom;

    private Animation animOpen, animClose;

    private Boolean isFabOpen = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container);
        ButterKnife.bind(this, rootView);

        animOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        animClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        fabBottom.setOnClickListener(l -> floatingAnim());

        return rootView;
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
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}
