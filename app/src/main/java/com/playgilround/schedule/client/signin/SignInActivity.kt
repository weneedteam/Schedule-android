package com.playgilround.schedule.client.signin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.facebook.CallbackManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kakao.auth.KakaoAdapter
import com.kakao.auth.KakaoSDK
import com.kakao.auth.Session
import com.nhn.android.naverlogin.OAuthLogin
import com.nispok.snackbar.Snackbar
import com.nispok.snackbar.SnackbarManager
import com.nispok.snackbar.enums.SnackbarType
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.main.MainActivity
import com.playgilround.schedule.client.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class SignInActivity : Activity(), SignInContract.View {

    private lateinit var mPresenter: SignInContract.Presenter

    private val PERMISSION_STORAGE = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET)

    private lateinit var fbCallbackManager: CallbackManager
    private var loginType = 0x0000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        TedPermission.with(this)
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {

                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    }
                })
                .setDeniedMessage(getString(R.string.message_check_permission))
                .setPermissions(*PERMISSION_STORAGE)
                .check()

        SignInPresenter(this, this)

        if (mPresenter.checkAutoSignIn()) {
            mPresenter.autoSignIn()
        }

        setOnClickListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (loginType) {
            FACEBOOK_LOGIN -> {
                fbCallbackManager.onActivityResult(requestCode, resultCode, data)
            }
            KAKAO_LOGIN -> {
                if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
                    return
                }
                super.onActivityResult(requestCode, resultCode, data)
            }
            GOOGLE_LOGIN -> {
                mPresenter.firebaseAuthGoogle(data)
            }
        }
        loginType = 0x0000
    }

    fun setOnClickListener() {
        ivlogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            mPresenter.signIn(email, password)
        }

        ivfacebook.setOnClickListener {
            fbCallbackManager = mPresenter.facebookSignIn(this)
            loginType = FACEBOOK_LOGIN
        }

        ivnaver.setOnClickListener {
            val mOAuthLogin = mPresenter.naverInit()
            mPresenter.naverSignIn(this, mOAuthLogin)
        }

        ivkakao.setOnClickListener {
            mPresenter.kakaoSignIn(this)
            loginType = KAKAO_LOGIN
        }

        ivgoogle.setOnClickListener {
            startActivityForResult(mPresenter.googleSignIn(), GOOGLE_LOGIN)
            loginType = GOOGLE_LOGIN
        }

        tvSignUp.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
    }

    override fun showProgressBar() {
        progress_sign_in.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_sign_in.visibility = View.INVISIBLE
    }

    override fun signInComplete() {
        hideProgressBar()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun signInError(status: Int) {
        hideProgressBar()
        when (status) {
            SignInPresenter.ERROR_EMAIL -> {
                showSnackBar(getString(R.string.error_sign_in_email_filed_check))
            }
            SignInPresenter.ERROR_PASSWORD -> {
                showSnackBar(getString(R.string.error_sign_in_password_filed_check))
            }
            SignInPresenter.ERROR_FAIL_SIGN_IN -> {
                showSnackBar(getString(R.string.error_sign_in_not_match_email_and_password_filed_check))
            }
            SignInPresenter.ERROR_NETWORK_CUSTOM -> {
                showSnackBar(getString(R.string.error_network))
            }
        }
    }

    private fun showSnackBar(content: String) {
        SnackbarManager.show(Snackbar.with(this)
                .type(SnackbarType.MULTI_LINE)
                .actionLabel(getString(R.string.error_text_close))
                .actionColorResource(R.color.error_snack_bar)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .text(content))
    }

override fun setPresenter(presenter: SignInContract.Presenter) {
    mPresenter = presenter
}

companion object {
    const val FACEBOOK_LOGIN = 0x0001
    const val KAKAO_LOGIN = 0x0002
    const val GOOGLE_LOGIN = 0x0003
}
}