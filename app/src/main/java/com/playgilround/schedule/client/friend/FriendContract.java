package com.playgilround.schedule.client.friend;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.signup.model.User;

/**
 * 19-03-14
 * Friend Contract
 */
public interface FriendContract {

    interface View extends BaseView<Presenter> {

        void searchError(int status);

        void searchResult(User result);

    }

    interface Presenter extends BasePresenter {

        void onSearch(String name);

    }
}
