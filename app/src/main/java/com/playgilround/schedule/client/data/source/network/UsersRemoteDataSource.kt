package com.playgilround.schedule.client.data.source.network

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.auth.*
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.data.User
import com.playgilround.schedule.client.data.source.UsersDataSource
import com.playgilround.schedule.client.retrofit.APIClient
import com.playgilround.schedule.client.retrofit.BaseUrl
import com.playgilround.schedule.client.retrofit.RestAuthAPI
import com.playgilround.schedule.client.retrofit.UserAPI
import com.playgilround.schedule.client.signin.SignInPresenter.Companion.ERROR_EMAIL
import com.playgilround.schedule.client.signin.SignInPresenter.Companion.ERROR_FAIL_SIGN_IN
import com.playgilround.schedule.client.signin.SignInPresenter.Companion.ERROR_NETWORK_CUSTOM
import com.playgilround.schedule.client.signin.SignInPresenter.Companion.ERROR_PASSWORD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRemoteDataSource @Inject constructor(val context: Context) : UsersDataSource, UsersDataSource.SNSLogin {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isKakaoLogin = false

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

    override fun facebookLogin(activity: Activity, loginCallBack: UsersDataSource.LoginCallBack): CallbackManager {
        val callbackManager = CallbackManager.Factory.create()
        var accessToken: String

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))

        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // 로그인 성공
                accessToken = AccessToken.getCurrentAccessToken().token
                postRestAuth(LOGIN_TYPE_FACEBOOK, accessToken, loginCallBack)
                Log.d("TESTLOG", "페이스북 로그인 성공 \ntoken:" + accessToken)
            }

            override fun onCancel() {
                // 로그인 취소
                Log.d("TESTLOG","페이스북 로그인 취소")
            }

            override fun onError(error: FacebookException?) {
                // 로그인 실패
                Log.d("TESTLOG","페이스북 로그인 실패")
            }
        })

        return callbackManager
    }

    override fun naverInit(): OAuthLogin {
        val mOAuthLoginModule   = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
                context,
                context.getString(R.string.naver_web_client_id),
                context.getString(R.string.naver_web_client_pw),
                context.getString(R.string.app_name)
        )
        return mOAuthLoginModule

    }

    override fun naverLogin(activity: Activity, oAuthLogin: OAuthLogin, loginCallBack: UsersDataSource.LoginCallBack) {

        var accessToken : String
        //var refreshToken : String
        //var expiresAt : Long
        //var tokenType : String

        val oAuthLoginHandler = object: OAuthLoginHandler() {
            override fun run(p0: Boolean) {
                if (p0) {
                    accessToken = oAuthLogin.getAccessToken(context)
                    postRestAuth(LOGIN_TYPE_NAVER, accessToken, loginCallBack)
                    Log.d("TESTLOG", "네이버 로그인 성공 \ntoken:" + accessToken)
                    //refreshToken = oAuthLogin.getRefreshToken(context)
                    //expiresAt = oAuthLogin.getExpiresAt(context)
                    //tokenType = oAuthLogin.getTokenType(context)
                } else {
                    val errorCode = oAuthLogin.getLastErrorCode(context).code
                    val errorDesc = oAuthLogin.getLastErrorDesc(context)
                    Log.d("TESTLOG", "네이버 로그인 실패 / errorCode: $errorCode / errorDesc: $errorDesc")
                    return
                }
            }
        }

        oAuthLogin.startOauthLoginActivity(activity, oAuthLoginHandler)
    }

    override fun kakaoLogin(activity: Activity, loginCallBack: UsersDataSource.LoginCallBack) {
        if (!isKakaoLogin) {
            KakaoSDK.init(KakaoSDKAdapter())
            isKakaoLogin = true
        }

        var kakaoCallback = SessionStatusCallback(loginCallBack)
        Session.getCurrentSession().addCallback(kakaoCallback)
        Session.getCurrentSession().checkAndImplicitOpen()
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, activity)
    }

    override fun googleLogin(): Intent {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.google_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)

        auth = FirebaseAuth.getInstance()

        return googleSignInClient.signInIntent
    }

    override fun firebaseAuthGoogle(data: Intent, loginCallBack: UsersDataSource.LoginCallBack) {

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)

            val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
            auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // 로그인 성공 시 처리
                            Log.d("TESTLOG", "구글 로그인 성공 \ntoken:" + account.idToken)
                            postRestAuth(LOGIN_TYPE_GOOGLE, account.idToken!!, loginCallBack)
                        } else {
                            // 로그인 실패 시 처리
                            Log.d("TESTLOG","구글 로그인 실패")
                        }
                    }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    private fun postRestAuth(type: Int, token: String, loginCallBack: UsersDataSource.LoginCallBack) {

        val retrofit = APIClient.getClient()
        val restAuthAPI = retrofit.create(RestAuthAPI::class.java)

        when(type) {
            LOGIN_TYPE_FACEBOOK -> {
                restAuthAPI.postFacebookLogin(token).enqueue(object : Callback<JsonObject>{
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("TESTLOG", "페이스북 우리 서버 로그인 성공 \ntoken:" + token)
                            loginCallBack.onUserLoaded()
                        } else {
                            loginCallBack.onDataNotAvailable(ERROR_FAIL_SIGN_IN)
                        }
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        loginCallBack.onDataNotAvailable(ERROR_NETWORK_CUSTOM)
                    }
                })
            }

            LOGIN_TYPE_NAVER -> {
                restAuthAPI.postNaverLogin(token).enqueue(object : Callback<JsonObject>{
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("TESTLOG", "네이버 우리 서버 로그인 성공 \ntoken:" + token)
                            loginCallBack.onUserLoaded()
                        } else {
                            loginCallBack.onDataNotAvailable(ERROR_FAIL_SIGN_IN)
                        }
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        loginCallBack.onDataNotAvailable(ERROR_NETWORK_CUSTOM)
                    }
                })
            }

            LOGIN_TYPE_KAKAO -> {
                restAuthAPI.postKakaoLogin(token).enqueue(object : Callback<JsonObject>{
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("TESTLOG", "카카오 우리 서버 로그인 성공 \ntoken:" + token)
                            loginCallBack.onUserLoaded()
                        } else {
                            loginCallBack.onDataNotAvailable(ERROR_FAIL_SIGN_IN)
                        }
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        loginCallBack.onDataNotAvailable(ERROR_NETWORK_CUSTOM)
                    }
                })
            }

            LOGIN_TYPE_GOOGLE -> {
                restAuthAPI.postGoogleLogin(token).enqueue(object : Callback<JsonObject>{
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("TESTLOG", "구글 우리 서버 로그인 성공 \ntoken:" + token)
                            loginCallBack.onUserLoaded()
                        } else {
                            loginCallBack.onDataNotAvailable(ERROR_FAIL_SIGN_IN)
                        }
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        loginCallBack.onDataNotAvailable(ERROR_NETWORK_CUSTOM)
                    }
                })
            }

        }
    }

    private fun checkEmail(email: String): Boolean {
        val mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
        val p = Pattern.compile(mail)
        val m = p.matcher(email)
        return m.matches()
    }

    inner class KakaoSDKAdapter: KakaoAdapter() {

        override fun getSessionConfig(): ISessionConfig {
            return object: ISessionConfig {
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType? {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return object: IApplicationConfig {
                override fun getApplicationContext(): Context {
                    return context
                }
            }
        }
    }

    inner class SessionStatusCallback(var loginCallBack: UsersDataSource.LoginCallBack): ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {

        }

        override fun onSessionOpened() {
            UserManagement.getInstance().me(object: MeV2ResponseCallback() {
                override fun onSessionClosed(errorResult: ErrorResult?) {
                }

                override fun onSuccess(result: MeV2Response?) {
                    Log.d("TESTLOG", "카카오 로그인 성공 \ntoken:" + Session.getCurrentSession().tokenInfo.accessToken)
                    postRestAuth(LOGIN_TYPE_KAKAO, Session.getCurrentSession().tokenInfo.accessToken, loginCallBack)
                }
            })
        }
    }

    companion object {
        const val LOGIN_TYPE_FACEBOOK = 0x0001
        const val LOGIN_TYPE_NAVER = 0x0002
        const val LOGIN_TYPE_KAKAO = 0x0003
        const val LOGIN_TYPE_GOOGLE = 0x0004
    }

}