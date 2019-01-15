package com.playgilround.schedule.client.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
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
public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleViewHolder>  {

    private ArrayList<ScheduleCard> arrScheduleCard;
//    private Listener mListener;

    private ArrayList arrTime, arrTitle, arrDesc;

//    public ScheduleCardAdapter(ArrayList time, ArrayList title, ArrayList desc, Listener listener) {
    public ScheduleCardAdapter(ArrayList<ScheduleCard> scheduleCard) {
        arrScheduleCard = scheduleCard;

        Log.d("TEST", "arrScheduleCard- >" + arrScheduleCard.size());
      /*  arrTime = time;
        arrTitle = title;
        arrDesc = desc;*/
//        mListener = listener;
    }


    @Override
    public ScheduleCardAdapter.ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("TEST", "onCreateViewHolder");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_schedule_card_item, viewGroup, false);

        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScheduleCardAdapter.ScheduleViewHolder viewHolder, int i) {
        ScheduleCard schedule = arrScheduleCard.get(i);
        Log.d("TEST" , "Bind ->" + schedule.title);
        Log.d("TEST" , "Bind ->" + i);
        viewHolder.tvTime.setText(arrScheduleCard.get(i).time);
        viewHolder.tvTitle.setText(arrScheduleCard.get(i).title);
        viewHolder.tvDesc.setText(arrScheduleCard.get(i).desc);

//        if (mListener != null) {
//            viewHolder.cardView.setOnClickListener(this);
//            viewHolder.cardView.setTag("TAG");
//        }
    }

    @Override
    public int getItemCount() {
        return arrScheduleCard.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
//        private ImageView ivSchedule;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvDesc;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
//            ivSchedule = itemView.findViewById(R.id.ivSchedule);
            cardView = itemView.findViewById(R.id.card_view);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }

 /*   @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            ScheduleCard schedule = (ScheduleCard) v.getTag();
            mListener.onItemClick(schedule);
        }
    }*/

//    public List<ScheduleCard> getItems() {
//        return mItems;
//    }

/*    public interface Listener {
        void onItemClick(ScheduleCard card);
    }*/
}
