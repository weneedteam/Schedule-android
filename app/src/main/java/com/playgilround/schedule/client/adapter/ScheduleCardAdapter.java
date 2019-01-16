package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.ScheduleCard;

import java.util.ArrayList;
import java.util.List;

/**
 * 19-01-13 스케줄 관련 CardView Adapter
 */
//public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleViewHolder> implements View.OnClickListener {
public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleViewHolder> implements View.OnClickListener {

    static final String TAG = ScheduleCardAdapter.class.getSimpleName();

    private List<ScheduleCard> mItems;

    private Listener mListener;
    private Context context;
    private ArrayList arrTime, arrTitle, arrDesc;

    public ScheduleCardAdapter(ArrayList time, ArrayList title, ArrayList desc, Listener listener) {
     /*   if (items == null) {
            items = new ArrayList<>();
        }

        mItems = items;*/

        arrTime = time;
        arrTitle = title;
        arrDesc = desc;
        mListener = listener;
        Log.d("TEST", "ScheduleCardAdapter.");
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("TEST", "onCreateViewHolder ->" + arrTitle.get(0).toString());
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_schedule_card_item, viewGroup, false);

        ScheduleViewHolder sh = new ScheduleViewHolder(v);
        return sh;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindView ->");
        viewHolder.tvTime.setText(arrTime.get(i).toString());
        viewHolder.tvTitle.setText(arrTitle.get(i).toString());
        viewHolder.tvDesc.setText(arrDesc.get(i).toString());
        if (mListener != null) {
            viewHolder.cardView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "arrTitle ->" + arrTitle.size());
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

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {

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
