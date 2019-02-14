package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.ScheduleInfo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 19-01-13 스케줄 관련 CardView Adapter
 */
public class ScheduleInfoAdapter extends RecyclerView.Adapter<ScheduleInfoAdapter.ScheduleViewHolder> implements View.OnClickListener {

    static final String TAG = ScheduleInfoAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<ScheduleInfo> arrCard;

    // private ScheduleInfoContract.Presenter mPresenter;

    public ScheduleInfoAdapter(Context context, ArrayList<ScheduleInfo> arrCard) {
        this.context = context;
        this.arrCard = arrCard;
    }

    @Nonnull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@Nonnull ViewGroup viewGroup, int i) {
        return new ScheduleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_schedule_info_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@Nonnull ScheduleViewHolder viewHolder, int i) {
        //  mPresenter.onBindViewScheduleInfo(viewHolder, i);
        DateTime dateTime = new DateTime(arrCard.get(i).time, DateTimeZone.getDefault());
        String time = dateTime.plusHours(9).toString(context.getString(R.string.text_date_time));

        viewHolder.tvTime.setText(time);
        viewHolder.tvTitle.setText(arrCard.get(i).title);
        viewHolder.tvDesc.setText(arrCard.get(i).desc);

        viewHolder.cardView.setOnClickListener(this);
        viewHolder.cardView.setTag(arrCard.get(i).id);
    }

    @Override
    public int getItemCount() {
        // return mPresenter.getScheduleCount();
        return arrCard.size();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            int tag = (int) v.getTag();
            // mPresenter.onItemClick(tag);
        }
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        // private ImageView ivSchedule;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvDesc;

        ScheduleViewHolder(View itemView) {
            super(itemView);
            // ivSchedule = itemView.findViewById(R.id.ivSchedule);
            cardView = itemView.findViewById(R.id.card_view);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
