package com.playgilround.schedule.client.friend;

import android.app.Activity;
import android.os.Bundle;

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
public class FriendActivity extends Activity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private static final String TAG = FriendActivity.class.getSimpleName();

    RecyclerView.LayoutManager mLayoutManager;
    FriendAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FriendAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }
}
