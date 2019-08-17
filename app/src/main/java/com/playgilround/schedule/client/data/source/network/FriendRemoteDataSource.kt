package com.playgilround.schedule.client.data.source.network

import android.content.Context
import com.playgilround.schedule.client.data.source.FriendDataSource
import com.playgilround.schedule.client.model.ResponseMessage
import io.reactivex.Single

class FriendRemoteDataSource constructor(val context: Context): FriendDataSource {

    override fun getFriendList(): Single<ResponseMessage> {

    }

}