package com.playgilround.schedule.client.data.source.network

import android.content.Context
import com.playgilround.schedule.client.data.source.FriendDataSource
import com.playgilround.schedule.client.model.ResponseMessage
import com.playgilround.schedule.client.retrofit.APIClient
import com.playgilround.schedule.client.retrofit.FriendAPI
import io.reactivex.Single

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRemoteDataSource @Inject constructor(private val mHttpUtils: com.playgilround.schedule.client.util.HttpUtils): FriendDataSource {
//class FriendRemoteDataSource @Inject constructor(private val mContext: Context): FriendDataSource {

    private lateinit var friendAPI: FriendAPI


    override fun getFriendList(): Single<ResponseMessage> {
        val retrofit = APIClient.getClient()
        val friendAPI = retrofit.create(FriendAPI::class.java)
//        return getFriendList()
//        friendAPI.friendList().enq
    }

    fun getFriendService(): FriendAPI {
        if (null == friendAPI) {
            friendAPI = mHttpUtils.getRetrofitKey()!!.create(FriendAPI::class.java)
        }
        return friendAPI
    }
}
