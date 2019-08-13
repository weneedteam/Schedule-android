package com.playgilround.schedule.client.addschedule.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AddScheduleAdapter constructor(private val mContext: Context): RecyclerView.Adapter<AddScheduleAdapter.RootViewHolder>() {

    abstract inner class RootViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        open fun bind(position: Int) {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {

    }

    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}