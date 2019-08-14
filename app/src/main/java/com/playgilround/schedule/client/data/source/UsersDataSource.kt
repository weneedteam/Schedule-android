package com.playgilround.schedule.client.data.source

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import com.facebook.CallbackManager
import com.google.gson.Gson
import com.nhn.android.naverlogin.OAuthLogin
import com.playgilround.schedule.client.data.User

interface UsersDataSource {

    interface LoginCallBack {

        fun onUserLoaded()

        fun onDataNotAvailable(error: Int)

    }

    fun getCurrentUser(context: Context): User? {
        val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val userString = pref.getString(PREF_KEY_USER, "")
        return Gson().fromJson<User>(userString, User::class.java)
    }

    fun saveCurrentUser(context: Context, user: User) {
        val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(PREF_KEY_USER, Gson().toJson(user))
        editor.apply()
    }

    fun login(email: String, password: String, loginCallBack: LoginCallBack)

    fun tokenLogin(loginCallBack: LoginCallBack)

    interface SNSLogin {
        fun facebookLogin(activity: Activity, loginCallBack: LoginCallBack): CallbackManager

        fun naverInit(): OAuthLogin

        fun naverLogin(activity: Activity, oAuthLogin: OAuthLogin, loginCallBack: LoginCallBack)

        fun kakaoLogin(activity: Activity, loginCallBack: LoginCallBack)

        fun googleLogin(): Intent

        fun firebaseAuthGoogle(data: Intent, loginCallBack: LoginCallBack)
    }

    companion object {
        private val TAG = UsersDataSource::class.java.simpleName
        val PREF_NAME = TAG + "_user.pref"
        val PREF_KEY_USER = TAG + "_user"
    }

}