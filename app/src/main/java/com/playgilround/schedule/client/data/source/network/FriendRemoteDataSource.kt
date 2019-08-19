package com.playgilround.schedule.client.data.source.network

import android.content.Context
import com.playgilround.schedule.client.data.FriendList
import com.playgilround.schedule.client.data.source.FriendDataSource
import com.playgilround.schedule.client.model.BaseResponse
import com.playgilround.schedule.client.model.ResponseMessage
import com.playgilround.schedule.client.retrofit.APIClient
import com.playgilround.schedule.client.retrofit.FriendAPI
import com.playgilround.schedule.client.util.HttpUtils
import io.reactivex.Single

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRemoteDataSource @Inject constructor(private val mHttpUtils: HttpUtils): FriendDataSource {
    private lateinit var friendAPI: FriendAPI


    override fun getFriendList(): Single<BaseResponse<FriendList>>? {
        return getFriendService().friendList()
    }

    private fun getFriendService(): FriendAPI {
        if (null == friendAPI) {
            friendAPI = mHttpUtils.getRetrofitKey()!!.create(FriendAPI::class.java)
        }
        return friendAPI
    }
}
