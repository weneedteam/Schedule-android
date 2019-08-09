package com.playgilround.schedule.client.tutorial

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import butterknife.OnClick
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.data.repository.UsersRepository
import com.playgilround.schedule.client.data.source.local.UsersLocalDataSource
import com.playgilround.schedule.client.data.source.network.UsersRemoteDataSource
import com.playgilround.schedule.client.signin.SignInActivity
import com.playgilround.schedule.client.tutorial.view.TutorialAdapter
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity: AppCompatActivity(), TutorialContract.View {

    private lateinit var adapter: TutorialAdapter
    private var retPosition: Int = 0

    private lateinit var mPresenter: TutorialContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tutorial)

        list_tutorial.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        list_tutorial.setHasFixedSize(true)

        TutorialPresenter(this, this)

        adapter = TutorialAdapter(this)
        list_tutorial.adapter = adapter

        with(list_tutorial) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val mLinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

                    retPosition = mLinearLayoutManager.findFirstVisibleItemPosition()

                    if (adapter!!.itemCount - 1 == retPosition) ivNextBtn.setImageResource(R.drawable.image_check)
                    else ivNextBtn.setImageResource(R.drawable.image_next)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val animator = ObjectAnimator.ofInt(progress_tutorial,
                                "progress", (retPosition + 1) * 100)
                        animator.duration = 500
                        animator.interpolator = DecelerateInterpolator()
                        animator.start()
                    }
                }
            })
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(list_tutorial)

        mPresenter.checkLogin()

        ivNextBtn.setOnClickListener {
            when (retPosition) {
                adapter.itemCount - 1 -> {
                    startActivity(Intent(this, SignInActivity::class.java))
                    overridePendingTransition(R.anim.enter, R.anim.exit)
                    finish()
                }
                adapter.itemCount - 2 -> {
                    ivNextBtn.setImageResource(R.drawable.image_check)
                    list_tutorial.smoothScrollToPosition(retPosition + 1)
                }
                else -> {
                    ivNextBtn.setImageResource(R.drawable.image_next)
                    list_tutorial.smoothScrollToPosition(retPosition + 1)
                }
            }
        }
    }

    override fun setPresenter(presenter: TutorialContract.Presenter) {
        mPresenter = presenter
    }

    override fun skipTutorial() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}