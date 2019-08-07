package com.playgilround.schedule.client.data.source.local

import android.content.Context
import com.playgilround.schedule.client.data.source.UsersDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersLocalDataSource @Inject constructor(val context: Context) : UsersDataSource {

    override fun login(email: String, password: String, loginCallBack: UsersDataSource.LoginCallBack) {
    }

    override fun tokenLogin(loginCallBack: UsersDataSource.LoginCallBack) {
    }
}