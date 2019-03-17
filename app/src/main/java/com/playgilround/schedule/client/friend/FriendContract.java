package com.playgilround.schedule.client.friend;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

/**
 * 19-03-14
 * Friend Contract
 */
public interface FriendContract {

    interface View extends BaseView<Presenter> {
        //친구 프로필 클릭 시
        void onProfileClick(int id);
    }

    interface Presenter extends BasePresenter {

    }
}
