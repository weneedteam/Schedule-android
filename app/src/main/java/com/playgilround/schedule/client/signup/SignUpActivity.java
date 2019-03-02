package com.playgilround.schedule.client.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.signup.view.SignUpAdapter;
import com.playgilround.schedule.client.signin.SignInActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View, SignUpAdapter.OnButtonClick {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private SignUpContract.Presenter mPresenter;

    @BindView(R.id.recycler_signup)
    RecyclerView mRecyclerView;

    SignUpAdapter adapter;

    int retPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        new SignUpPresenter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerView.stopScroll();
            }
        });

        mRecyclerView.setHasFixedSize(true);

        adapter = new SignUpAdapter(this, this);
        mRecyclerView.setAdapter(adapter);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
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

  /*  @OnClick(R.id.btnNext)
    public void signUp() {

       *//* User user = new User();

        user.setNickName(mNickNameEditText.getText().toString());
        user.setUserName(mNameEditText.getText().toString());
        user.setEmail(mEmailEditText.getText().toString());
        user.setPassword(mPasswordEditText.getText().toString());
        user.setBirth(mBirthEditText.getText().toString());
        user.setLanguage("Korean");

        mPresenter.signUp(user);*//*
    }*/

    @Override
    public void onNextClick(int position) {
        retPosition = position;

        if (adapter.getItemCount() == retPosition) {
            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, SignInActivity.class));
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        } else {
            mRecyclerView.smoothScrollToPosition(retPosition);
        }
    }

    @Override
    public void onBackClick(int position) {
        retPosition = position;
        if (retPosition == -1) {
            finish();
        } else {
            mRecyclerView.smoothScrollToPosition(retPosition);
        }
    }



    @Override
    public void signUpError() {

    }

    @Override
    public void singUpComplete() {

    }
}
