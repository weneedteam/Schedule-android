package com.playgilround.schedule.client.singin;

import android.os.Bundle;

import com.playgilround.schedule.client.R;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new SignInPresenter(this);
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void signInComplete() {

    }

    @Override
    public void signInError() {

    }
}
