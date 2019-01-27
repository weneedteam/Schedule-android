package com.playgilround.schedule.client.singin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.activity.MainActivity;
import com.playgilround.schedule.client.activity.ScheduleCalendarActivity;
import com.playgilround.schedule.client.model.User;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        new SignInPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void signIn() {
        startActivity(new Intent(this, ScheduleCalendarActivity.class));
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
