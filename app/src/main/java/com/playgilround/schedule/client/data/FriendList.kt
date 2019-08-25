package com.playgilround.schedule.client.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FriendList {

    @SerializedName("friends")
    @Expose
    var friendsId: List<Int>? = null
}