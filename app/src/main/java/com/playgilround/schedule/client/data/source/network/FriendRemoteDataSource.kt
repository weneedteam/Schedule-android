package com.playgilround.schedule.client.data.source.network

import android.content.Context
import com.playgilround.schedule.client.data.source.FriendDataSource
import com.playgilround.schedule.client.model.BaseResponse
import com.playgilround.schedule.client.model.ResponseMessage
import com.playgilround.schedule.client.retrofit.APIClient
import com.playgilround.schedule.client.retrofit.BaseUrl
import com.playgilround.schedule.client.retrofit.FriendAPI
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRemoteDataSource @Inject constructor(val context: Context): FriendDataSource {

    override fun getFriendList(): Single<ResponseMessage> {
        val retrofit = APIClient.getClient()
        val friendAPI = retrofit.create(FriendAPI::class.java)
//        return getFriendList()
//        friendAPI.friendList().enq
    }
}