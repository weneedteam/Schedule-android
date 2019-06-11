package com.playgilround.schedule.client.signin;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.playgilround.schedule.client.base.BasePresenter;
import com.playgilround.schedule.client.base.BaseView;

public interface SignInContract {

    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void hideProgressBar();

        void signInComplete();

        void signInError(int status);

    }

    interface Presenter extends BasePresenter {

        boolean checkAutoSignIn();

        void autoSignIn();

        void signIn(String email, String password);

        Intent googleSingIn();

        void firebaseAuthGoogle(GoogleSignInAccount acct);

    }

}
