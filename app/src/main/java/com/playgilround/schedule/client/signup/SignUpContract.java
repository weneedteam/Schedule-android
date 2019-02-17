package com.playgilround.schedule.client.signup;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;
import com.playgilround.schedule.client.signup.model.User;

public interface SignUpContract {

    interface View extends BaseView<Presenter> {

        void signUpError();

        void singUpComplete();

    }

    interface Presenter extends BasePresenter {

        void signUp(User user);

    }

}
