package com.playgilround.schedule.client.friend;

import android.content.Context;

/**
 * 19-03-14
 * 친구 관련 데이터 처리
 */
public class FriendPresenter implements FriendContract.Presenter {

    private final FriendContract.View mView;
    private final Context mContext;

    FriendPresenter(Context context, FriendContract.View view) {
        mView = view;
        mContext = context;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
