package com.playgilround.schedule.client.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val mContext: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!HttpUtils.isOnline(mContext)) {
            throw IOException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}