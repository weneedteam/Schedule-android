package com.playgilround.schedule.client.signup;

import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

public interface SignUpContract {

    interface View extends BaseView<Presenter> {

        void fieldCheck(boolean check);

        void signUpError(int status);

        void singUpComplete();

    }

    interface Presenter extends BasePresenter {

        void onClickNext(int position);

        void onClickBack(int position);

        void signUp();

    }
}
