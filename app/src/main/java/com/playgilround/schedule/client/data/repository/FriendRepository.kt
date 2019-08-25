package com.playgilround.schedule.client.data.repository

import com.playgilround.schedule.client.data.FriendList
import com.playgilround.schedule.client.data.source.FriendDataSource
import com.playgilround.schedule.client.data.source.network.FriendRemoteDataSource
import com.playgilround.schedule.client.model.BaseResponse
import com.playgilround.schedule.client.model.ResponseMessage
import io.reactivex.Single

class FriendRepository(private val friendRemoteDataSource: FriendRemoteDataSource): FriendDataSource {

    override fun getFriendList(): Single<BaseResponse<FriendList>>? {
        return friendRemoteDataSource.getFriendList()
    }
}
