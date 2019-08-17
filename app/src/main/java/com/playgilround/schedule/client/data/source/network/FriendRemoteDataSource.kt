package com.playgilround.schedule.client.data.source.network

import android.content.Context
import com.playgilround.schedule.client.data.source.FriendDataSource
import com.playgilround.schedule.client.model.ResponseMessage
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRemoteDataSource @Inject constructor(val context: Context): FriendDataSource {

    override fun getFriendList(): Single<ResponseMessage> {

    }

}