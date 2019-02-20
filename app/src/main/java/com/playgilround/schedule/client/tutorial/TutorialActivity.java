package com.playgilround.schedule.client.tutorial;

import android.os.Bundle;
import android.util.Log;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.adapter.TutorialAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-01-31
 * Tutorial Activity
 */
public class TutorialActivity extends AppCompatActivity implements TutorialContract.View {

    @BindView(R.id.recycler_image)
    RecyclerView mRecyclerView;

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
            }
        });

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void setPresenter(TutorialContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
