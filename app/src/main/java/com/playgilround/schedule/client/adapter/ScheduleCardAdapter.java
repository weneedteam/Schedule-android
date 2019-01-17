package com.playgilround.schedule.client.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.ScheduleCard;

import java.util.ArrayList;

import javax.annotation.Nonnull;

/**
 * 19-01-13 스케줄 관련 CardView Adapter
 */
public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleViewHolder> implements View.OnClickListener {

    static final String TAG = ScheduleCardAdapter.class.getSimpleName();

    private Listener mListener;
    private ArrayList arrTime, arrTitle, arrDesc;

    public ScheduleCardAdapter(ArrayList time, ArrayList title, ArrayList desc, Listener listener) {
        arrTime = time;
        arrTitle = title;
        arrDesc = desc;
        mListener = listener;
    }

    @Nonnull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@Nonnull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_schedule_card_item, viewGroup, false);

        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@Nonnull ScheduleViewHolder viewHolder, int i) {
        viewHolder.tvTime.setText(arrTime.get(i).toString());
        viewHolder.tvTitle.setText(arrTitle.get(i).toString());
        viewHolder.tvDesc.setText(arrDesc.get(i).toString());
        if (mListener != null) {
            viewHolder.cardView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return arrTime.size();
    }


    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            ScheduleCard schedule = (ScheduleCard) v.getTag();
            mListener.onItemClick(schedule);
        }
    }

    public interface Listener {
        void onItemClick(ScheduleCard card);
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        //private ImageView ivSchedule;
        TextView tvTime;
        TextView tvTitle;
        TextView tvDesc;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            //ivSchedule = itemView.findViewById(R.id.ivSchedule);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
