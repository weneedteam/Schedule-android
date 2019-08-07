package com.playgilround.schedule.client.tutorial.view

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.playgilround.schedule.client.R
import kotlinx.android.synthetic.main.item_tutorial.view.*

class TutorialAdapter constructor(private val mContext: Context) : RecyclerView.Adapter<TutorialAdapter.TutorialViewHolder>() {

    companion object {
        const val ITEM_COUNT = 4
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        return create(LayoutInflater.from(parent.context), parent, viewType)
    }

    private fun create(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): TutorialViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)

        return TutorialViewHolder(binding, mContext)
    }
    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 1) {
            R.layout.item_tutorial_logo
        } else {
            R.layout.item_tutorial_illustration
        }
    }

    class TutorialViewHolder constructor(private val binding: ViewDataBinding, private val mContext: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(position: Int) {
            val texts = mContext.resources.getStringArray(R.array.tutorial_text_array)
            val text = texts[position]

            binding.root.ivtutorial.tag = position
            val ssb = SpannableStringBuilder(text)
            val startText: Int
            val lastText: Int

            when (position) {
                0 -> {
                    startText = 15
                    lastText = 17
                }
                1 -> {
                    startText = 12
                    lastText = 14
                }
                2 -> {
                    startText = 9
                    lastText = 12
                }
                else -> {
                    startText = 8
                    lastText = 11
                }
            }

            ssb.setSpan(ForegroundColorSpan(mContext.resources.getColor(R.color.light_indigo)), startText, lastText, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.root.tvtutorial.text = ssb
        }
    }
}

