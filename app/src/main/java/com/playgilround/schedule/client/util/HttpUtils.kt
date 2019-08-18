package com.playgilround.schedule.client.util

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import com.playgilround.schedule.client.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpUtils constructor(private val mContext: Context) {
    private val API_BASE_URL = "http://192.168.150.11:8000/"

    private var retrofitKey: Retrofit? = null

    fun getRetrofitKey(): Retrofit? {
        if (null == retrofitKey) {
            retrofitKey = provideRetrofit(API_BASE_URL, provideClientKey())
        }
        return retrofitKey
    }

    private fun provideClientKey(): OkHttpClient {

        val interceptor: Interceptor

        if (BuildConfig.DEBUG) {
            interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor = ConnectivityInterceptor(mContext)
        }

        return OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder().build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()
    }

    private fun provideRetrofit(baseURL: String, client: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

   companion object {

       private var INSTANCE: HttpUtils? = null

       fun isOnline(context: Context): Boolean {
           val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val networkInfo = connectivityManager.activeNetworkInfo
           return (networkInfo != null && networkInfo.isConnected)
       }

       fun getInstance(context: Context): HttpUtils {
           if (null == INSTANCE) {
               INSTANCE = HttpUtils(context)
           }
           return INSTANCE as HttpUtils
       }
   }


}