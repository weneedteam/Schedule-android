package com.playgilround.schedule.client.friend;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.data.User;

/**
 * 19-03-14
 * Friend Contract
 */
public interface FriendContract {

    interface View extends BaseView<Presenter> {

        void searchError(int status);

        void searchFind(User result);

        //친구 프로필 클릭 시
        void onProfileClick(int id);

        void onCheckResult(User result);

        void setFriendList();

        void updateFriendList();
    }

    interface Presenter extends BasePresenter {

        void onSearch(String name);

        void onCheckFriend(User result);

        void getFriendList();

        void onRequestFriend();

        void onRequestCancel();
    }
}
