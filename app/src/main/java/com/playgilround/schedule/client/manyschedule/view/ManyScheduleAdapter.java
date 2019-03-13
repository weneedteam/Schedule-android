package com.playgilround.schedule.client.manyschedule.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playgilround.schedule.client.R;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-02-07
 * 스케줄 다중 선택 어댑터
 */
public class ManyScheduleAdapter extends RecyclerView.Adapter<ManyScheduleAdapter.ViewHolder> {

    private ArrayList<String> arrTime;

    public ManyScheduleAdapter(ArrayList<String> arrTime) {
        this.arrTime = arrTime;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_many_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@Nonnull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return arrTime.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvSelectTime)
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            mTextView.setText(arrTime.get(position));
        }
    }
}
