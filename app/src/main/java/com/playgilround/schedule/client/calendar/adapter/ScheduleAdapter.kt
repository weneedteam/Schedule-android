package com.playgilround.schedule.client.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R

class ScheduleAdapter constructor(context: Context): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return 10 //test
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindView(position: Int) {

        }
    }


}