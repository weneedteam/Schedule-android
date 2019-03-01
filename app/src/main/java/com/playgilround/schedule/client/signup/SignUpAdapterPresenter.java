package com.playgilround.schedule.client.signup;

public class SignUpAdapterPresenter implements SignUpAdapterContract.Presenter {

    private final SignUpAdapterContract.View mView;

    public SignUpAdapterPresenter(SignUpAdapterContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
