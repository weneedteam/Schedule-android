package com.playgilround.schedule.client.addschedule.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R
import java.lang.IllegalArgumentException

class AddScheduleAdapter constructor(private val mContext: Context): RecyclerView.Adapter<AddScheduleAdapter.RootViewHolder>() {

    private lateinit var mTitleViewHolder: TitleViewHolder
    private lateinit var mDateViewHolder: DateViewHolder
    private lateinit var mMemberViewHolder: MemberViewHolder
    private lateinit var mPlaceViewHolder: PlaceViewHolder
    private lateinit var mMemoViewHolder: MemoViewHolder
    private lateinit var mMapViewHolder: MapViewHolder
    private lateinit var mResultViewholder: ResultViewHolder

    abstract inner class RootViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        open fun bind(position: Int) {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        return when (viewType) {
            TYPE_SCHEDULE_TITLE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
                mTitleViewHolder = TitleViewHolder(view)
                return mTitleViewHolder
            }
            TYPE_SCHEDULE_DATE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
                mDateViewHolder = DateViewHolder(view)
                return mDateViewHolder
            }
            TYPE_SCHEDULE_MEMBER -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
                mMemberViewHolder = MemberViewHolder(view)
                return mMemberViewHolder
            }
            TYPE_SCHEDULE_PLACE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
                mPlaceViewHolder = PlaceViewHolder(view)
                return mPlaceViewHolder
            }
            TYPE_SCHEDULE_MEMO -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.item_sign_up_birth, parent, false)
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
                mResultViewholder = ResultViewHolder(view)
                return mResultViewholder
            }
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TitleViewHolder(itemView: View): RootViewHolder(itemView)

    inner class DateViewHolder(itemView: View): RootViewHolder(itemView)

    inner class MemberViewHolder(itemView: View): RootViewHolder(itemView)

    inner class PlaceViewHolder(itemView: View): RootViewHolder(itemView)

    inner class MemoViewHolder(itemView: View): RootViewHolder(itemView)

    inner class MapViewHolder(itemView: View): RootViewHolder(itemView)

    inner class ResultViewHolder(itemView: View): RootViewHolder(itemView)

    override fun getItemCount(): Int {
        return 5
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        const val TYPE_SCHEDULE_TITLE = 0
        const val TYPE_SCHEDULE_DATE = 1
        const val TYPE_SCHEDULE_MEMBER = 2
        const val TYPE_SCHEDULE_PLACE = 3
        const val TYPE_SCHEDULE_MEMO = 4
        const val TYPE_SCHEDULE_MAP = 5
        const val TYPE_SCHEDULE_RESULT = 6
    }
}