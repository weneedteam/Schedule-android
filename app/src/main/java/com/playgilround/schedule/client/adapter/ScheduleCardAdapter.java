package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.ScheduleCard;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 19-01-13 스케줄 관련 CardView Adapter
 */
public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleViewHolder> implements View.OnClickListener  {

    private ArrayList<ScheduleCard> arrScheduleCard;
    private Listener mListener;
    static final String TAG = ScheduleCardAdapter.class.getSimpleName();
    private Context context;
    private ArrayList arrId, arrTime, arrTitle, arrDesc;

    public ScheduleCardAdapter(Context context, ArrayList id, ArrayList time, ArrayList title, ArrayList desc, Listener listener) {
        this.context = context;
        arrId = id;
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
        DateTime dateTime = new DateTime(Long.valueOf(arrTime.get(i).toString()), DateTimeZone.UTC);
        String strTime = dateTime.plusHours(9).toString(context.getString(R.string.text_date_time));
        viewHolder.tvTime.setText(strTime);
        viewHolder.tvTitle.setText(arrTitle.get(i).toString());
        viewHolder.tvDesc.setText(arrDesc.get(i).toString());

        if (mListener != null) {
            viewHolder.cardView.setOnClickListener(this);
            viewHolder.cardView.setTag(arrId.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return arrTime.size();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            int schedule = (int) v.getTag();
            mListener.onItemClick(schedule);
        }
    }
    public interface Listener {
        void onItemClick(int schedule);
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
//        private ImageView ivSchedule;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvDesc;

        ScheduleViewHolder(View itemView) {
            super(itemView);
//            ivSchedule = itemView.findViewById(R.id.ivSchedule);
            cardView = itemView.findViewById(R.id.card_view);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
