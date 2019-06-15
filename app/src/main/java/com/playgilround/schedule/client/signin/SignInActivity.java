package com.playgilround.schedule.client.signin;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.data.repository.UsersRepository;
import com.playgilround.schedule.client.data.source.local.UsersLocalDataSource;
import com.playgilround.schedule.client.data.source.network.UsersRemoteDataSource;
import com.playgilround.schedule.client.main.MainActivity;
import com.playgilround.schedule.client.signup.SignUpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nispok.snackbar.Snackbar.with;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;

    @BindView(R.id.progress_sign_in)
    ProgressBar mProgressBar;

    @BindView(R.id.etEmail)
    EditText mEditTextEmail;

    @BindView(R.id.etPassword)
    EditText mEditTextPassword;

    private String[] PERMISSION_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    static final int GOOGLE_LOGIN = 0x0001;

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
        new SignInPresenter(this, this,
                new UsersRepository(
                        UsersLocalDataSource.getInstance(this),
                        UsersRemoteDataSource.getInstance(this)));

        if (mPresenter.checkAutoSignIn()) {
            mPresenter.autoSignIn();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_LOGIN) {
            mPresenter.firebaseAuthGoogle(data);
        }
    }

    @OnClick(R.id.ivlogin)
    void onLoginClick() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        mPresenter.signIn(email, password);
    }

    @OnClick(R.id.ivgoogle)
    void onGoolgeLoginClick() {
        startActivityForResult(mPresenter.googleSingIn(), GOOGLE_LOGIN);
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
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void signInComplete() {
        hideProgressBar();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void signInError(int status) {
        hideProgressBar();
        switch (status) {
            case SignInPresenter.ERROR_EMAIL:
                showSnackBar(getString(R.string.error_sign_in_email_filed_check));
                break;
            case SignInPresenter.ERROR_PASSWORD:
                showSnackBar(getString(R.string.error_sign_in_password_filed_check));
                break;
            case SignInPresenter.ERROR_FAIL_SIGN_IN:
                showSnackBar(getString(R.string.error_sign_in_not_match_email_and_password_filed_check));
                break;
            case SignInPresenter.ERROR_NETWORK_CUSTOM:
                showSnackBar(getString(R.string.error_network));
                break;
        }
    }

    private void showSnackBar(String content) {
        SnackbarManager.show(with(this)
                .type(SnackbarType.MULTI_LINE)
                .actionLabel(getString(R.string.error_text_close))
                .actionColorResource(R.color.error_snack_bar)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .text(content));
    }

    private void dismissSnackBar() {
        SnackbarManager.dismiss();
    }
}
