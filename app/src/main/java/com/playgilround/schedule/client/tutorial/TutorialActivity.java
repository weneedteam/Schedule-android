package com.playgilround.schedule.client.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.tutorial.view.TutorialAdapter;
import com.playgilround.schedule.client.signin.SignInActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 19-01-31
 * Tutorial Activity
 */
public class TutorialActivity extends AppCompatActivity implements TutorialContract.View {

    @BindView(R.id.recycler_image)
    RecyclerView mRecyclerView;

    @BindView(R.id.ivNextBtn)
    ImageView mImageNext;

    int retPosition;

    TutorialAdapter adapter;

    private TutorialContract.Presenter mPresenter;

    static final String TAG = TutorialActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);

        new TutorialPresenter(this);

        adapter = new TutorialAdapter(this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager mLinear = (LinearLayoutManager) recyclerView.getLayoutManager();

                retPosition = mLinear.findFirstVisibleItemPosition();
                if (adapter.getItemCount() -1 == retPosition) {
                    mImageNext.setImageResource(R.mipmap.check);
                } else {
                    mImageNext.setImageResource(R.mipmap.next_btn);
                }
            }
        });

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

    }

    @OnClick(R.id.ivNextBtn)
    void onButtonClick() {
        if (adapter.getItemCount() -1 == retPosition) {
            startActivity(new Intent(this, SignInActivity.class));
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        } else if (adapter.getItemCount() -2 == retPosition) {
            mImageNext.setImageResource(R.mipmap.check);
            mRecyclerView.smoothScrollToPosition(retPosition +1);
        } else {
            mImageNext.setImageResource(R.mipmap.tutorial_btn);
            mRecyclerView.smoothScrollToPosition(retPosition +1);
        }
    }

    @Override
    public void setPresenter(TutorialContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
