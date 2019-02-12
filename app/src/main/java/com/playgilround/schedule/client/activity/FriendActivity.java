package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.os.Bundle;

import com.playgilround.schedule.client.R;

import butterknife.ButterKnife;

/**
 * 19-02-11
 * 친구 관련 Fragment
 */
public class FriendActivity extends Activity {

    private static final String TAG = FriendActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);

    }
}
