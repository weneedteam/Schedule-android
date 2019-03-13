package com.playgilround.schedule.client.signin;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.signup.model.User;

public interface SignInContract {

    interface View extends BaseView<Presenter> {

        void signInComplete();

        void signInError(int status);

    }

    interface Presenter extends BasePresenter {

        boolean checkAutoSignIn();

        void autoSignIn();

        void signIn(String email, String password);

    }

}
