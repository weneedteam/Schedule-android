package com.playgilround.schedule.client.data.source

import com.playgilround.schedule.client.data.FriendList
import com.playgilround.schedule.client.model.BaseResponse
import com.playgilround.schedule.client.model.ResponseMessage
import io.reactivex.Single

interface FriendDataSource {
    fun getFriendList(): Single<BaseResponse<FriendList>>?
}