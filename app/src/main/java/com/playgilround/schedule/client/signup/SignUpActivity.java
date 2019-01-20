package com.playgilround.schedule.client.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.User;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private SignUpContract.Presenter mPresenter;

    @BindView(R.id.emailInput)
    EditText mEmailEditText;

    @BindView(R.id.pwInput)
    EditText mPasswordEditText;

    @BindView(R.id.nameInput)
    EditText mNameEditText;

    @BindView(R.id.nicknameInput)
    EditText mNickNameEditText;

    @BindView(R.id.birthInput)
    EditText mBirthEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        new SignUpPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.button_sign_up)
    public void signUp() {

        User user = new User();

        user.setNickName(mNickNameEditText.getText().toString());
        user.setUserName(mNameEditText.getText().toString());
        user.setEmail(mEmailEditText.getText().toString());
        user.setPassword(mPasswordEditText.getText().toString());
        user.setBirth(mBirthEditText.getText().toString());
        user.setLanguage("Korean");

        mPresenter.signUp(user);
    }

    @Override
    public void signUpError() {

    }

    @Override
    public void singUpComplete() {

    }
}
