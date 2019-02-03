package com.playgilround.schedule.client.signup;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.User;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private SignUpContract.Presenter mPresenter;

   /* @BindView(R.id.emailInput)
    EditText mEmailEditText;

    @BindView(R.id.pwInput)
    EditText mPasswordEditText;

    @BindView(R.id.nameInput)
    EditText mNameEditText;

    @BindView(R.id.nicknameInput)
    EditText mNickNameEditText;

    @BindView(R.id.birthInput)
    EditText mBirthEditText;*/

   @BindView(R.id.etText)
   EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        ButterKnife.bind(this);

        mEditText.post(() -> {
            mEditText.setFocusableInTouchMode(true);
            mEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditText,0);
        });
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

    @OnClick(R.id.btn_next)
    public void signUp() {

       /* User user = new User();

        user.setNickName(mNickNameEditText.getText().toString());
        user.setUserName(mNameEditText.getText().toString());
        user.setEmail(mEmailEditText.getText().toString());
        user.setPassword(mPasswordEditText.getText().toString());
        user.setBirth(mBirthEditText.getText().toString());
        user.setLanguage("Korean");

        mPresenter.signUp(user);*/
    }

    @Override
    public void signUpError() {

    }

    @Override
    public void singUpComplete() {

    }
}
