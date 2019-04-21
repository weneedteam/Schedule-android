package com.playgilround.schedule.client.data.source.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.playgilround.schedule.client.data.User
import com.playgilround.schedule.client.data.source.UsersDataSource
import com.playgilround.schedule.client.retrofit.APIClient
import com.playgilround.schedule.client.retrofit.BaseUrl
import com.playgilround.schedule.client.retrofit.UserAPI
import com.playgilround.schedule.client.signin.SignInPresenter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class UsersRemoteDataSource private constructor(val context: Context) : UsersDataSource {

    override fun login(email: String, password: String, loginCallBack: UsersDataSource.LoginCallBack) {
        if (checkEmail(email)) {
            loginCallBack.onDataNotAvailable(ERROR_EMAIL)
            return
        }

        if (password.isEmpty()) {
            loginCallBack.onDataNotAvailable(ERROR_PASSWORD)
            return
        }

        val retrofit = APIClient.getClient()
        val userAPI = retrofit.create(UserAPI::class.java)

        val jsonObject = JsonObject()
        jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_EMAIL, email)

        // Todo:: Server 측에 base64 encoding 제안
        // jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_PASSWORD, User.base64Encoding(password));
        jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_PASSWORD, password)

        userAPI.emailSignIn(jsonObject).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    val userInfo = response.body()!!.getAsJsonObject("user_info")
                    val user = Gson().fromJson(userInfo.get("user"), User::class.java)

                    val birth = userInfo.get("birth").asString
                    val language = userInfo.get("language").asString
                    val token = userInfo.get("token").asString

                    user.birth = birth
                    user.language = language
                    user.token = token

                    saveCurrentUser(context, user)
                    loginCallBack.onUserLoaded()
                } else {
                    loginCallBack.onDataNotAvailable(ERROR_FAIL_SIGN_IN)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // CrashlyticsCore.getInstance().log(t.toString());
                loginCallBack.onDataNotAvailable(ERROR_NETWORK_CUSTOM)
            }
        })
    }

    override fun tokenLogin(loginCallBack: UsersDataSource.LoginCallBack) {
        val currentUser = getCurrentUser(context)

        val retrofit = APIClient.getClient()
        val userAPI = retrofit.create(UserAPI::class.java)

        val jsonObject = JsonObject()
        jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_TOKEN, currentUser?.token)

        userAPI.tokenSignIn(jsonObject).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    val userInfo = response.body()!!.getAsJsonObject("user_info")
                    val user = Gson().fromJson(userInfo.get("user"), User::class.java)

                    val birth = userInfo.get("birth").asString
                    val language = userInfo.get("language").asString
                    val token = userInfo.get("token").asString

                    user.birth = birth
                    user.language = language
                    user.token = token

                    saveCurrentUser(context, user)
                    loginCallBack.onUserLoaded()
                } else {
                    loginCallBack.onDataNotAvailable(ERROR_FAIL_SIGN_IN)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // CrashlyticsCore.getInstance().log(t.toString());
                loginCallBack.onDataNotAvailable(ERROR_NETWORK_CUSTOM)
            }
        })
    }

    private fun checkEmail(email: String): Boolean {
        val mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
        val p = Pattern.compile(mail)
        val m = p.matcher(email)
        return m.matches()
    }

    companion object {
        private var INSTANCE: UsersRemoteDataSource? = null

        @JvmStatic
        fun getInstance(context: Context): UsersRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(UsersDataSource::javaClass) {
                    INSTANCE = UsersRemoteDataSource(context)
                }
            }
            return INSTANCE!!
        }
    }

}