package com.playgilround.schedule.client.singin;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.model.User;

public interface SignInContract {

    interface View extends BaseView<Presenter> {

        void signInComplete();

        void signInError();

    }

    interface Presenter extends BasePresenter {

        void signIn(User user);

    }

}
