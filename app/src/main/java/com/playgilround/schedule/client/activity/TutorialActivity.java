package com.playgilround.schedule.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.TutorialAdapter;
import com.playgilround.schedule.client.singin.SignInActivity;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

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
public class TutorialActivity extends AppCompatActivity {

    @BindView(R.id.recycler_image)
    RecyclerView mRecyclerView;

    @BindView(R.id.pager_indicator)
    IndefinitePagerIndicator indefinite;

    @BindView(R.id.btn_next)
    Button mNextBtn;

    int retPosition;

    TutorialAdapter adapter;

    static final String TAG = TutorialActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
        adapter = new TutorialAdapter(this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager mLinear = (LinearLayoutManager) recyclerView.getLayoutManager();

                retPosition = mLinear.findFirstVisibleItemPosition();
                if (retPosition == adapter.getItemCount() -1) {
                    mNextBtn.setText(R.string.button_start);
                } else {
                    mNextBtn.setText(R.string.button_next);
                }
            }
        });

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

        indefinite.attachToRecyclerView(mRecyclerView);
    }

    @OnClick(R.id.btn_next)
    void onButtonClick() {
        if (adapter.getItemCount() -1 == retPosition) {
            Log.d(TAG, "Button Click -----");
            startActivity(new Intent(this, SignInActivity.class));
            this.overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        } else {
            mRecyclerView.smoothScrollToPosition(retPosition + 1);
        }
    }
}
