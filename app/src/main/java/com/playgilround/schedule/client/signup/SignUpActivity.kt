package com.playgilround.schedule.client.signup

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.signup.view.OnSignUpAdapterListener
import com.playgilround.schedule.client.signup.view.SignUpAdapter
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity: AppCompatActivity(), SignUpContract.View {

    private lateinit var mAdapter: SignUpAdapter
    private lateinit var mPresenter: SignUpContract.Presenter
    private  var clickable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        recycler_sign_up.setHasFixedSize(true)
        recycler_sign_up.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_sign_up.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                recyclerView.stopScroll()
            }
        })

        mAdapter = SignUpAdapter(this)

        mAdapter.setOnSignUpNextFieldListener(object : OnSignUpAdapterListener {
            override fun onNextField(position: Int) {
                mPresenter.onClickBack(position)
            }

            override fun disableNextButton() {
                button_sign_up_next.setImageResource(R.drawable.disable_btn)
                clickable = false
            }

            override fun ableNextButton() {
                button_sign_up_next.setImageResource(R.drawable.image_next)
                clickable = true
            }
        })

        recycler_sign_up.adapter = mAdapter

        PagerSnapHelper().attachToRecyclerView(recycler_sign_up)

        setOnClickListener()
        SignUpPresenter(this, this, mAdapter)
    }

    fun setOnClickListener() {
        button_sign_up_next.setOnClickListener {
            if (clickable) mPresenter.onClickNext(mAdapter.position) }
    }

    private fun onBack() {
        button_sign_up_back.setOnClickListener {
            if (mAdapter.position != 0) {
                mPresenter.onClickBack(mAdapter.position)

                val animator = ObjectAnimator.ofInt(progress_sign_up,
                        "progress", progress_sign_up.progress -10)
                animator.duration = 500
                animator.interpolator = DecelerateInterpolator()
                animator.start()

                mAdapter.position = mAdapter.position -1
                recycler_sign_up.smoothScrollToPosition(mAdapter.position)
            } else {
                finish()
            }
        }
    }

    override fun fieldCheck(check: Boolean) {
        if (check) {
            if (mAdapter.position +1 != mAdapter.itemCount) {
                clickable = false

                val animator = ObjectAnimator.ofInt(progress_sign_up,
                        "progress", progress_sign_up.progress + 10)
                animator.duration = 500
                animator.interpolator = DecelerateInterpolator()
                animator.start()

                mAdapter.position = mAdapter.position +1
                recycler_sign_up.smoothScrollToPosition(mAdapter.position)

                Handler().postDelayed({ mAdapter.setFocus() }, 500)

                if (mAdapter.position != mAdapter.itemCount -1) button_sign_up_next.setImageResource(R.drawable.disable_btn)
                else {
                    button_sign_up_next.setImageResource(R.drawable.image_check)
                    clickable = true
                }
            } else {
                mPresenter.signUp()
            }
        } else {
            mAdapter.showSnackBar()
        }
    }

    override fun signUpError(status: Int) {
        when (status) {
            SignUpPresenter.ERROR_SIGN_UP -> Toast.makeText(this, "Sign Up Error", Toast.LENGTH_SHORT).show()
            SignUpPresenter.ERROR_NETWORK_CUSTOM -> Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun signUpComplete() {
        Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onBackPressed() {
        onBack()
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        mPresenter = presenter
    }


}