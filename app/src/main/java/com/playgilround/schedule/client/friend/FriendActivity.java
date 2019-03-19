package com.playgilround.schedule.client.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.friend.view.FriendAdapter;
import com.playgilround.schedule.client.profile.ProfileActivity;
import com.playgilround.schedule.client.signup.model.User;

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

        mAdapter = new FriendAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        ivMyProfile.setOnClickListener(l -> startActivity(new Intent(this, ProfileActivity.class)));
    }

    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void searchError(int status) {
        switch (status) {
            case FriendPresenter.ERROR_NETWORK_CUSTOM:
                Toast.makeText(this, "NetWork Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void searchResult(User result) {

    }
    public void onProfileClick(int id) {
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
