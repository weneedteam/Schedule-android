package com.playgilround.schedule.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.playgilround.schedule.client.R;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {


    private String[] images = new String[]{
            "tutorial", "illustration", "illustration"};

    private String[] texts = new String[]{
            "일정을 등록할 때,\n 장소를 설정해주세요.", "장소에 가까워지면\n 도착 버튼이 활성화됩니다.", "도착 버튼을 누르고\n 친구와 함께 경쟁하세요."};

    private Context mContext;

    public TutorialAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_tutorial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
            String text = texts[position];

            int resourceID = mContext.getResources().getIdentifier(image, "mipmap", mContext.getPackageName());

            mImageView.setTag(position);
            Picasso.get().load(resourceID).into(mImageView);

            mTextView.setText(text);
        }
    }
}
