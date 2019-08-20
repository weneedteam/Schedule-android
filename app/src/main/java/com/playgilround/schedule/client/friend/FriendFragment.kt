package com.playgilround.schedule.client.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.friend.view.FriendListAdapter
import com.playgilround.schedule.client.friend.view.FriendRequestAdapter
import kotlinx.android.synthetic.main.fragment_friend.*

class FriendFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rvRequestFriend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvRequestFriend.adapter = FriendRequestAdapter(context)

        rvFriend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvFriend.adapter = FriendListAdapter(context)
    }
}