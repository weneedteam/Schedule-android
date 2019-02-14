package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.model.ScheduleInfo;
import com.playgilround.schedule.client.schedule.info.ScheduleInfoContract;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-01-13 스케줄 관련 CardView Adapter
 */
public class ScheduleInfoAdapter extends RecyclerView.Adapter<ScheduleInfoAdapter.ScheduleViewHolder> implements View.OnClickListener {

    static final String TAG = ScheduleInfoAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<ScheduleInfo> arrCard;

    private ScheduleInfoContract.View mView;
    // private ScheduleInfoContract.Presenter mPresenter;

    public ScheduleInfoAdapter(Context context, ArrayList<ScheduleInfo> arrCard, ScheduleInfoContract.View mView) {
        this.context = context;
        this.arrCard = arrCard;
        this.mView = mView;
    }

    @Nonnull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@Nonnull ViewGroup viewGroup, int i) {
        return new ScheduleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_schedule_info_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@Nonnull ScheduleViewHolder holder, int i) {
        //  mPresenter.onBindViewScheduleInfo(viewHolder, i);
        DateTime dateTime = new DateTime(arrCard.get(i).time, DateTimeZone.getDefault());
        String time = dateTime.plusHours(9).toString(context.getString(R.string.text_date_time));

        holder.tvTime.setText(time);
        holder.tvTitle.setText(arrCard.get(i).title);
        holder.tvDesc.setText(arrCard.get(i).desc);

        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(arrCard.get(i).id);
    }

    @Override
    public int getItemCount() {
        return arrCard.size();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            int tag = (int) v.getTag();
            mView.onItemClick(tag);
        }
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvDesc)
        TextView tvDesc;

        ScheduleViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
