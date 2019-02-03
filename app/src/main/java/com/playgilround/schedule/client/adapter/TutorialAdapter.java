package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.playgilround.schedule.client.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {

    private static final String image1 = "tutorial";
    private static final String image2 = "illustration";

    private String[] images = new String[]{image1, image2, image2};

    private Context mContext;

    public TutorialAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_tutorial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
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
            String image = images[position];

            String[] texts = mContext.getResources().getStringArray(R.array.tutorial_text_array);

            String text = texts[position];

            int resourceID = mContext.getResources().getIdentifier(image, "mipmap", mContext.getPackageName());

            mImageView.setTag(position);
            Picasso.get().load(resourceID).into(mImageView);

            //특정 글자만 색깔 변경
            //ViewHolder 여러개 만들기
            SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            int startText;
            int lastText;
            if (position == 0) {
                startText = 12;
                lastText = 14;
            } else if (position == 1) {
                startText = 10;
                lastText = 12;
            } else {
                startText = 19;
                lastText = 21;
            }
            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.light_indigo)), startText, lastText, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTextView.setText(ssb);
        }
    }
}
