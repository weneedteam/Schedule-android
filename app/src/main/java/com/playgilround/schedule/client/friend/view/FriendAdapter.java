package com.playgilround.schedule.client.friend.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.playgilround.schedule.client.R;

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

    public FriendAdapter(Context context) {
        this.context = context;
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
        return 5;
    }
    class FriendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivFriendProfile)
        ImageView ivFriendProfile;

        FriendViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void onBind(int i) {
            ivFriendProfile.setOnClickListener(l -> Log.d(TAG, "Friend Adapter Click ->" + i));
        }
    }
}
