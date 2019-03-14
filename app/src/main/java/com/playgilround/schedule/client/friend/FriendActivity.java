package com.playgilround.schedule.client.friend;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.friend.view.FriendAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-02-11
 * 친구 관련 Fragment
 */
public class FriendActivity extends Activity implements FriendContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.ivMyProfile)
    ImageView ivMyProfile;

    private static final String TAG = FriendActivity.class.getSimpleName();

    RecyclerView.LayoutManager mLayoutManager;
    FriendAdapter mAdapter;

    private FriendContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);

        new FriendPresenter(this, this);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FriendAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        ivMyProfile.setOnClickListener(l -> Log.d(TAG, "My Profile Click"));
    }

    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
