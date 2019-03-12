package com.playgilround.schedule.client.signin;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.calendarschedule.CalendarScheduleActivity;
import com.playgilround.schedule.client.signup.SignUpActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;

    @BindView(R.id.etEmail)
    EditText mEditTextEmail;

    @BindView(R.id.etPassword)
    EditText mEditTextPassword;

    private String[] PERMISSION_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage(getString(R.string.message_check_permission))
                .setPermissions(PERMISSION_STORAGE)
                .check();

        ButterKnife.bind(this);
        new SignInPresenter(this, this);

        if (mPresenter.checkAutoSignIn()) {
            mPresenter.autoSignIn();
        }
    }

    @OnClick(R.id.ivlogin)
    void onLoginClick() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        mPresenter.signIn(email, password);
    }

    @OnClick(R.id.tvSignUp)
    void onSignUpClick() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void signInComplete() {
        startActivity(new Intent(this, CalendarScheduleActivity.class));
    }

    // Todo:: View 코드 작성 (현욱)
    @Override
    public void signInError(int status) {
        switch (status) {
            case SignInPresenter.ERROR_EMAIL:
                Toast.makeText(this, "Error Email", Toast.LENGTH_SHORT).show();
                break;
            case SignInPresenter.ERROR_PASSWORD:
                Toast.makeText(this, "Error Password", Toast.LENGTH_SHORT).show();
                break;
            case SignInPresenter.ERROR_FAIL_SIGN_IN:
                Toast.makeText(this, "Not match email and password", Toast.LENGTH_SHORT).show();
                break;
            case SignInPresenter.ERROR_NETWORK_CUSTOM:
                Toast.makeText(this, "NetWork Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
