package com.playgilround.schedule.client.friend.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.friend.FriendContract;

import javax.annotation.Nonnull;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-03-11
 * Friend Adapter
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private Context context;

    private static final String TAG = FriendAdapter.class.getSimpleName();

    private FriendContract.View mView;

    public FriendAdapter(Context context, FriendContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

    @Nonnull
    @Override
    public FriendViewHolder onCreateViewHolder(@Nonnull ViewGroup viewGroup, int i) {
        return new FriendViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@Nonnull FriendViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    class FriendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivFriendProfile)
        ImageView ivFriendProfile;

        FriendViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(int i) {
            ivFriendProfile.setOnClickListener(l -> mView.onProfileClick(i));
        }
    }
}
