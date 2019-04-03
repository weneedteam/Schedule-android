package com.playgilround.schedule.client.tutorial.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {

    private static final String TAG = TutorialAdapter.class.getSimpleName();

    private static final int TYPE_ILLUSTRATION = 0;
    private static final int TYPE_LOGO = 1;

    private static final int ITEM_COUNT = 4;

    private Context mContext;

    public TutorialAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ILLUSTRATION:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_tutorial_illustration, parent, false);
                return new ViewHolder(view);
            case TYPE_LOGO:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_tutorial_logo, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_tutorial, parent, false);
                return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 1 ? TYPE_LOGO : TYPE_ILLUSTRATION;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivtutorial)
        ImageView mImageView;

        @BindView(R.id.tvtutorial)
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            String[] texts = mContext.getResources().getStringArray(R.array.tutorial_text_array);
            String text = texts[position];

            mImageView.setTag(position);

            //특정 글자만 색깔 변경
            //ViewHolder 여러개 만들기
            SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            int startText;
            int lastText;
            if (position == 0) {
                startText = 15;
                lastText = 17;
            } else if (position == 1) {
                startText = 12;
                lastText = 14;
            } else if (position == 2) {
                startText = 9;
                lastText = 12;
            } else {
                startText = 8;
                lastText = 11;
            }
            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.light_indigo)), startText, lastText, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTextView.setText(ssb);
        }
    }
}
