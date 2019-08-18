package com.playgilround.schedule.client.addschedule

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.addschedule.view.AddScheduleAdapter
import com.playgilround.schedule.client.base.BaseFragment
import com.playgilround.schedule.client.data.FriendList
import com.playgilround.schedule.client.util.OnEditorAdapterListener
import kotlinx.android.synthetic.main.add_schedule_frag.*

class TestAddScheduleFragment: BaseFragment(), TestAddScheduleContract.View {

    private lateinit var mPresenter: TestAddScheduleContract.Presenter
    private lateinit var mAdapter: AddScheduleAdapter
    private lateinit var mFriendList: FriendList
    private var clickable = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = AddScheduleAdapter(context)
        mAdapter.setOnScheduleNextFieldListener(object : OnEditorAdapterListener {
            override fun onNextField(position: Int) {
                mPresenter.onClickBack(position)
            }

            override fun disableNextButton() {
                schedule_next_btn.setImageResource(R.drawable.disable_btn)
                clickable = false
            }

            override fun ableNextButton() {
                schedule_next_btn.setImageResource(R.drawable.image_next)
                clickable = true
            }
        })
        TestAddSchedulePresenter(context, this, mAdapter)
        mPresenter.getFriendList()
        return  inflater.inflate(R.layout.add_schedule_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_add_schedule.adapter = mAdapter

        recycler_add_schedule.setHasFixedSize(true)
        recycler_add_schedule.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_add_schedule.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                recyclerView.stopScroll()
            }
        })
        PagerSnapHelper().attachToRecyclerView(recycler_add_schedule)

        setOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.rxUnSubscribe()
    }

    fun setOnClickListener() {
        schedule_next_btn.setOnClickListener {
            Log.d("TEST", "Position --${mAdapter.position}")
            mPresenter.onClickNext(mAdapter.position)
        }
    }

    override fun fieldCheck(check: Boolean) {
        if (check) {
            if (mAdapter.position +1 != mAdapter.itemCount) {
                clickable = false

                mAdapter.position = mAdapter.position +1
                recycler_add_schedule.smoothScrollToPosition(mAdapter.position)

                Handler().postDelayed({ mAdapter.setFocus() }, 500)

                if (mAdapter.position != mAdapter.itemCount -1) schedule_next_btn.setImageResource(R.drawable.disable_btn)
                else {
                    schedule_next_btn.setImageResource(R.drawable.image_check)
                    clickable = true
                }
            } else {
                mPresenter.scheduleSave()
            }
        } else {
            mAdapter.showSnackBar()
        }
    }

    override fun setFriendInfo(result: String) {
       }
/*    override fun setFriendInfo(list: FriendList) {
        mFriendList = list
        mAdapter = AddScheduleAdapter(context, mFriendList)
        recycler_add_schedule.adapter = mAdapter
        mAdapter.setOnScheduleNextFieldListener(object : OnEditorAdapterListener {
            override fun onNextField(position: Int) {
                mPresenter.onClickBack(position)
            }

            override fun disableNextButton() {
                schedule_next_btn.setImageResource(R.drawable.disable_btn)
                clickable = false
            }

            override fun ableNextButton() {
                schedule_next_btn.setImageResource(R.drawable.image_next)
                clickable = true
            }
        })
    }*/
    override fun setPresenter(presenter: TestAddScheduleContract.Presenter) {
        mPresenter = presenter
    }


    companion object {
        fun newInstance(): TestAddScheduleFragment {
            return TestAddScheduleFragment()
        }
    }
}