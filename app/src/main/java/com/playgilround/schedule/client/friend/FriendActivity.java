package com.playgilround.schedule.client.friend;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.friend.view.FriendAdapter;
import com.playgilround.schedule.client.profile.ProfileFragment;
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

        mAdapter = new FriendAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        ivMyProfile.setOnClickListener(l -> {
            final ProfileFragment fragment = ProfileFragment.getInstance();
            final FragmentManager fm = getFragmentManager();
            fragment.show(fm, "TAG");
        });
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
}
