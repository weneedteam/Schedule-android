package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.activity.TutorialActivity;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {

    private static final String image1 = "tutorial";
    private static final String image2 = "illustration";

    private String[] images = new String[]{image1, image2, image2};

    private Context mContext;
    int retPosition;

    static final String TAG = TutorialAdapter.class.getSimpleName();

    public boolean isFirst = false;

    public TutorialAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_tutorial, parent, false);
        Log.d(TAG, "onCreateViewHolder ...");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!isFirst) {
            retPosition = position;
        }
        Log.d(TAG, "retPosition - > " +retPosition + "//" + holder.getLayoutPosition() + "//" + holder.getAdapterPosition());
        holder.bindView(retPosition);
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

        @BindView(R.id.btn_next)
        Button mButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            String image = images[position];

            String[] texts = new String[]{
                    mContext.getString(R.string.text_tutorial_start1),
                    mContext.getString(R.string.text_tutorial_start2),
                    mContext.getString(R.string.text_tutorial_start3)};

            String text = texts[position];

            int resourceID = mContext.getResources().getIdentifier(image, "mipmap", mContext.getPackageName());

            mImageView.setTag(position);
            Picasso.get().load(resourceID).into(mImageView);

            //특정 글자만 색깔 변경
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

        @OnClick(R.id.btn_next)
        void onButtonClick(View v) {
            Log.d(TAG, "onButtonClick -> " +retPosition);

            bindView(retPosition +1);
        }

    }
}
