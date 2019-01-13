package com.playgilround.schedule.client.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.ScheduleCard;

import java.util.ArrayList;
import java.util.List;

/**
 * 19-01-13 스케줄 관련 CardView Adapter
 */
public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleViewHolder> implements View.OnClickListener {

    private List<ScheduleCard> mItems;
    private Listener mListener;

    public ScheduleCardAdapter(List<ScheduleCard> items, Listener listener) {
        if (items == null) {
            items = new ArrayList<>();
        }

        mItems = items;
        mListener = listener;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_schedule_card_item, viewGroup, false);

        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder viewHolder, int i) {
        ScheduleCard schedule = mItems.get(i);

        //Test
        viewHolder.tvTime.setText("2019-01-13 22:22:22");
        viewHolder.tvTitle.setText("테스트");
        viewHolder.tvDesc.setText("테스트");

        if (mListener != null) {
            viewHolder.cardView.setOnClickListener(this);
            viewHolder.cardView.setTag(schedule);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView ivSchedule;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvDesc;

        private ScheduleViewHolder(View itemView) {
            super(itemView);
            ivSchedule = itemView.findViewById(R.id.ivSchedule);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            ScheduleCard schedule = (ScheduleCard) v.getTag();
            mListener.onItemClick(schedule);
        }
    }

    public List<ScheduleCard> getItems() {
        return mItems;
    }

    public interface Listener {
        void onItemClick(ScheduleCard card);
    }
}
