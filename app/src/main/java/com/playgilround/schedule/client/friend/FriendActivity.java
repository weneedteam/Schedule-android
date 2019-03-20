package com.playgilround.schedule.client.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
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
public class FriendActivity extends Activity implements FriendContract.View, MaterialSearchBar.OnSearchActionListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.ivMyProfile)
    ImageView ivMyProfile;

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;

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

        searchBar.setOnSearchActionListener(this);
    }

    //SearchBar Click
    @Override
    public void onButtonClicked(int buttonCode) {

    }

    //SearchBar InputText Changed
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    //Call SearchBar Search
    @Override
    public void onSearchConfirmed(CharSequence text) {
        mPresenter.onSearch(text.toString());
    }


    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void searchError(int status) {
        switch (status) {
            case FriendPresenter.ERROR_NETWORK_CUSTOM:
                Toast.makeText(this, getString(R.string.toast_msg_network_error), Toast.LENGTH_SHORT).show();
                break;
            case FriendPresenter.FAIL_USER_FOUND:
                Toast.makeText(this, getString(R.string.toast_msg_user_not_found), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void searchFind(User result) {
        Toast.makeText(this, result.getNickname(), Toast.LENGTH_LONG).show();
    }

    public void onProfileClick(int id) {
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
