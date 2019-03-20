package com.playgilround.schedule.client.friend;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.signup.model.User;

/**
 * 19-03-14
 * Friend Contract
 */
public interface FriendContract {

    interface View extends BaseView<Presenter> {

        void searchError(int status);

        void searchFind(User result);

        void searchFail(ResponseMessage result);

        //친구 프로필 클릭 시
        void onProfileClick(int id);
    }

    interface Presenter extends BasePresenter {

        void onSearch(String name);

    }
}
