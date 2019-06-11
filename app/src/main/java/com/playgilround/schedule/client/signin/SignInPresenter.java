package com.playgilround.schedule.client.signin;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.playgilround.schedule.client.data.repository.UsersRepository;
import com.playgilround.schedule.client.data.source.UsersDataSource;

public class SignInPresenter implements SignInContract.Presenter, UsersDataSource.LoginCallBack {

    private static final String TAG = SignInPresenter.class.getSimpleName();

    public static final int ERROR_EMAIL = 0x0001;
    public static final int ERROR_PASSWORD = 0x0002;
    public static final int ERROR_FAIL_SIGN_IN = 0x0003;
    public static final int ERROR_NETWORK_CUSTOM = 0x0004;

    private final Context mContext;
    private final SignInContract.View mView;
    private final UsersRepository mRepository;

    SignInPresenter(Context context, SignInContract.View view, UsersRepository repository) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;
        mRepository = repository;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean checkAutoSignIn() {
        return mRepository.getCurrentUser(mContext) != null;
    }

    @Override
    public void autoSignIn() {
        mView.showProgressBar();
        mRepository.tokenLogin(this);
    }

    @Override
    public void signIn(String email, String password) {
        mView.showProgressBar();
        mRepository.login(email, password, this);
    }

    @Override
    public Intent googleSingIn() {
        return mRepository.googleLogin();
    }

    @Override
    public void firebaseAuthGoogle(GoogleSignInAccount acct) {
        mRepository.firebaseAuthGoogle(acct);
    }

    @Override
    public void onUserLoaded() {
        mView.signInComplete();
    }

    @Override
    public void onDataNotAvailable(int error) {
        mView.signInError(error);
    }
}
