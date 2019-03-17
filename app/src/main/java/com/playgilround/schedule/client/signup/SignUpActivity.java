package com.playgilround.schedule.client.signup;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.signup.view.SignUpAdapter;
import com.playgilround.schedule.client.signup.view.OnSignUpAdapterListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.recycler_sign_up)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_sign_up)
    ProgressBar mProgressBar;

    @BindView(R.id.button_sign_up_next)
    ImageView mNextButton;

    private SignUpContract.Presenter mPresenter;
    private SignUpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerView.stopScroll();
            }
        });

        mAdapter = new SignUpAdapter(this);
        mAdapter.setOnSignUpNextFieldListener(new OnSignUpAdapterListener() {
            @Override
            public void onNextField(int position) {
                mPresenter.onClickNext(position);
            }

            @Override
            public void disableNextButton() {
                mNextButton.setImageResource(R.drawable.disable_btn);
            }

            @Override
            public void ableNextButton() {
                mNextButton.setImageResource(R.drawable.next_btn);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

        new SignUpPresenter(this, mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.button_sign_up_next)
    public void onNext() {
        mPresenter.onClickNext(mAdapter.getPosition());
    }

    @OnClick(R.id.button_sign_up_back)
    public void onBack() {
        if (mAdapter.getPosition() != 0) {
            mPresenter.onClickBack(mAdapter.getPosition());

            ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar,
                    "progress", mProgressBar.getProgress() - 10);
            animator.setDuration(500);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();

            mAdapter.setPosition(mAdapter.getPosition() - 1);
            mRecyclerView.smoothScrollToPosition(mAdapter.getPosition());
        } else {
            finish();
        }
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
    public void fieldCheck(boolean check) {
        if (check) {
            if (mAdapter.getPosition() + 1 != mAdapter.getItemCount()) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar,
                        "progress", mProgressBar.getProgress() + 10);
                animator.setDuration(500);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();

                mAdapter.setPosition(mAdapter.getPosition() + 1);
                mRecyclerView.smoothScrollToPosition(mAdapter.getPosition());
                new Handler().postDelayed(() -> mAdapter.setFocus(), 500);

                mNextButton.setImageResource(R.drawable.disable_btn);
            } else {
                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show();
                // 이전 activity 에서 finish() 된게 아니라서 startActivity 할 필요가 없음.
                // 애니메이션 동작 안함.
                /*startActivity(new Intent(this, SignInActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);*/
                finish();
            }
        } else {
            mAdapter.showSnackBar();
        }
    }

    @Override
    public void signUpError() {

    }

    @Override
    public void singUpComplete() {

    }
}
