package com.playgilround.schedule.client.util

import com.playgilround.schedule.client.BuildConfig
import com.playgilround.schedule.client.ScheduleApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class HttpUtils {
    internal val API_BASE_URL = "http://192.168.150.11:8000/"

    private var retrofitKey: Retrofit? = null

    fun getRetrofitKey(): Retrofit? {
        if (null == retrofitKey) {
            retrofitKey = provideRetrofit(API_BASE_URL, provideClientKey())
        }
        return retrofitKey
    }

    fun provideClientKey(): OkHttpClient {

        val interceptor: Interceptor

        if (BuildConfig.DEBUG) {
            interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor = Connectivit
        }
    }

   companion object {
       val connectivityManager =
   }


}