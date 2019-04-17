package com.playgilround.schedule.client.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.data.User;
import com.playgilround.schedule.client.friend.view.FriendAdapter;
import com.playgilround.schedule.client.profile.ProfileActivity;

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

    @BindView(R.id.linearFriend)
    LinearLayout linearFriend;

    @BindView(R.id.tvMyNickName)
    TextView tvMyNickName;

    @BindView(R.id.tvMyName)
    TextView tvMyName;

    @BindView(R.id.ivFriendRequest)
    ImageView ivRequest;

    @BindView(R.id.tvFriendRequest)
    TextView tvRequest;

    RecyclerView.LayoutManager mLayoutManager;
    FriendAdapter mAdapter;

    private FriendContract.Presenter mPresenter;

    private static final String TAG = FriendPresenter.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);

        new FriendPresenter(this, this);

        mPresenter.getFriendList();

        ivMyProfile.setOnClickListener(l -> startActivity(new Intent(this, ProfileActivity.class)));

        //친구 요청
        ivRequest.setOnClickListener(view -> mPresenter.onRequestFriend());

        //친구 요청 취소
        tvRequest.setOnClickListener(view -> mPresenter.onRequestCancel());

        searchBar.setOnSearchActionListener(this);
    }

    @Override
    public void setFriendList() {
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FriendAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
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
        mPresenter.onCheckFriend(result);
    }

    //검색 한 유저 결과
    @Override
    public void onCheckResult(User result) {

        linearFriend.setVisibility(View.GONE);

        //추후에 프로필 사진까지 표시.
        tvMyNickName.setText(result.getNickname());
        tvMyName.setText(result.getUsername());

        tvRequest.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateFriendList() {
        linearFriend.setVisibility(View.VISIBLE);
        tvRequest.setVisibility(View.GONE);
        ivRequest.setVisibility(View.GONE);
        mPresenter.getFriendList();
    }

    public void onProfileClick(int id) {
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
