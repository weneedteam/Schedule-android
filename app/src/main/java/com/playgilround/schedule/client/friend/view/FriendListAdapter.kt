package com.playgilround.schedule.client.friend.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.profile.TestProfileActivity

/**
 * 19-08-17
 * FriendAdapter 클래스랑 중복?
 */
class FriendListAdapter constructor(private val mContext: Context?): RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    abstract inner class RootViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        open fun bind(position: Int) {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val rootView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend, parent, false)

        val holder = FriendViewHolder(rootView)
        holder.itemView.setOnClickListener {
            mContext?.startActivity(Intent(mContext, TestProfileActivity::class.java))
        }
        return holder
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class FriendViewHolder(itemView: View): RootViewHolder(itemView) {

    }
}