package com.playgilround.schedule.client.addschedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.addschedule.model.ScheduleDataModel
import com.playgilround.schedule.client.data.FriendList
import com.playgilround.schedule.client.signup.view.SignUpAdapter
import com.playgilround.schedule.client.util.OnEditorAdapterListener
import java.lang.IllegalArgumentException

class AddScheduleAdapter constructor(private val mContext: Context?, private val friendList: FriendList): RecyclerView.Adapter<AddScheduleAdapter.RootViewHolder>(), ScheduleDataModel{

    var position = 0
    private lateinit var mTitleViewHolder: TitleViewHolder
    private lateinit var mDateViewHolder: DateViewHolder
    private lateinit var mMemberViewHolder: MemberViewHolder
    private lateinit var mPlaceViewHolder: PlaceViewHolder
    private lateinit var mMemoViewHolder: MemoViewHolder
    private lateinit var mMapViewHolder: MapViewHolder
    private lateinit var mResultViewHolder: ResultViewHolder

    private lateinit var mOnEditorAdapterListener: OnEditorAdapterListener

    abstract inner class RootViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var textContent: String? = null

        fun getContent(): String? {
            return textContent
        }

        abstract fun bind(position: Int)
    }
    abstract inner class EditViewHolder(itemView: View): RootViewHolder(itemView) {

        val mEditSchedule = itemView.findViewById(R.id.edit_schedule) as EditText

        internal var viewPosition: Int = 0

        private val mOnEditorActionListener = TextView.OnEditorActionListener {v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE && mOnEditorAdapterListener != null) {
                mOnEditorAdapterListener.onNextField(viewPosition)
                return@OnEditorActionListener true
            }
            false
        }

        abstract fun checkEditText(content: String): Boolean

        override fun bind(position: Int) {
            viewPosition = position
            mEditSchedule.setOnEditorActionListener(mOnEditorActionListener)
            mEditSchedule.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = mEditSchedule.text.toString().trim()
                    if (checkEditText(text)) {
                        textContent = text
                        mOnEditorAdapterListener.ableNextButton()
                    } else {
                        mOnEditorAdapterListener.disableNextButton()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }

        fun setFocus() {
            mEditSchedule.requestFocus()
        }
    }

    abstract inner class RecyclerViewHolder(itemView: View): RootViewHolder(itemView) {

        val mEditSchedule = itemView.findViewById(R.id.edit_schedule) as EditText
        val mRecyclerView = itemView.findViewById(R.id.rvMemberList) as RecyclerView

        @SuppressLint("WrongConstant")
        override fun bind(position: Int) {
            mRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            mRecyclerView.adapter = FriendListAdapter(mContext)
        }
    }

    abstract inner class EmptyViewHolder(itemView: View): RootViewHolder(itemView) {

        override fun bind(position: Int) {
            mOnEditorAdapterListener.ableNextButton()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        return when (viewType) {
            TYPE_SCHEDULE_TITLE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_add_schedule_title, parent, false)
                mTitleViewHolder = TitleViewHolder(view)
                return mTitleViewHolder
            }
            TYPE_SCHEDULE_DATE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_add_schedule_date, parent, false)
                mDateViewHolder = DateViewHolder(view)
                return mDateViewHolder
            }
            TYPE_SCHEDULE_MEMBER -> {
                Log.d("TEST", "MemberViewHolder")
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_add_schedule_member, parent, false)
                mMemberViewHolder = MemberViewHolder(view)
                return mMemberViewHolder
            }
            TYPE_SCHEDULE_PLACE -> {
                Log.d("TEST", "PlaceViewHolder")
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_add_schedule_place, parent, false)
                mPlaceViewHolder = PlaceViewHolder(view)
                return mPlaceViewHolder
            }
            TYPE_SCHEDULE_MEMO -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_add_schedule_memo, parent, false)
                mMemoViewHolder = MemoViewHolder(view)
                return mMemoViewHolder
            }
            TYPE_SCHEDULE_MAP -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
                mMapViewHolder = MapViewHolder(view)
                return mMapViewHolder
            }
            TYPE_SCHEDULE_RESULT -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
                mResultViewHolder = ResultViewHolder(view)
                return mResultViewHolder
            }
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }


    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TitleViewHolder(itemView: View): EditViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return content.isNotEmpty()
        }
    }

    inner class DateViewHolder(itemView: View): EmptyViewHolder(itemView) {

    }

    inner class MemberViewHolder(itemView: View): RecyclerViewHolder(itemView) {

    }

    inner class PlaceViewHolder(itemView: View): EditViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return content.isNotEmpty()
        }
    }

    inner class MemoViewHolder(itemView: View): EditViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return content.isNotEmpty()
        }
    }

    inner class MapViewHolder(itemView: View): EmptyViewHolder(itemView) {

    }

    inner class ResultViewHolder(itemView: View): EmptyViewHolder(itemView) {

    }

    override fun getItemCount(): Int {
        return SCHEDULE_MAX
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setOnScheduleNextFieldListener(onEditorAdapterListener: OnEditorAdapterListener) {
        mOnEditorAdapterListener = onEditorAdapterListener
    }

    override fun getScheduleTitle(): String? {
        return mTitleViewHolder.getContent()
    }

    fun setFocus() {
        when (this.position) {
            TYPE_SCHEDULE_TITLE -> mTitleViewHolder.setFocus()
            TYPE_SCHEDULE_DATE -> null
            TYPE_SCHEDULE_MEMBER -> null
            TYPE_SCHEDULE_PLACE -> mPlaceViewHolder.setFocus()
            TYPE_SCHEDULE_MEMO -> mMemoViewHolder.setFocus()
            TYPE_SCHEDULE_MAP -> null
            TYPE_SCHEDULE_RESULT -> null
        }
    }

    fun showSnackBar() {

    }

    fun dismissSnackBar() {

    }

    companion object {
        const val TYPE_SCHEDULE_TITLE = 0
        const val TYPE_SCHEDULE_DATE = 1
        const val TYPE_SCHEDULE_MEMBER = 2
        const val TYPE_SCHEDULE_PLACE = 3
        const val TYPE_SCHEDULE_MEMO = 4
        const val TYPE_SCHEDULE_MAP = 5
        const val TYPE_SCHEDULE_RESULT = 6
        const val SCHEDULE_MAX = 7
    }
}