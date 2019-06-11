package com.playgilround.schedule.client.data.source.local

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.playgilround.schedule.client.data.source.UsersDataSource

class UsersLocalDataSource private constructor(val context: Context) : UsersDataSource {

    override fun login(email: String, password: String, loginCallBack: UsersDataSource.LoginCallBack) {
    }

    override fun tokenLogin(loginCallBack: UsersDataSource.LoginCallBack) {
    }

    companion object {
        private var INSTANCE: UsersLocalDataSource? = null

        @JvmStatic
        fun getInstance(context: Context): UsersLocalDataSource {
            if (INSTANCE == null) {
                synchronized(UsersDataSource::javaClass) {
                    INSTANCE = UsersLocalDataSource(context)
                }
            }
            return INSTANCE!!
        }
    }

}