package com.playgilround.schedule.client.signup.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.playgilround.schedule.client.R;

import javax.annotation.Nonnull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 19-02-23
 * SignUp Adapter
 */
public class SignUpAdapter extends RecyclerView.Adapter<SignUpAdapter.ViewHolder> {

    private Context mContext;

    public SignUpAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_signup, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSignUpTitle)
        TextView tvTitle;

        @BindView(R.id.tvSignUpContent)
        TextView tvContent;

        @BindView(R.id.progress)
        ProgressBar mProgress;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            String[] titles = mContext.getResources().getStringArray(R.array.signup_text_title_array);
            String title = titles[position];

            String[] contents = mContext.getResources().getStringArray(R.array.signup_text_content_array);
            String content = contents[position];

            tvTitle.setText(title);
            tvContent.setText(content);
            mProgress.setProgress(position +1);
        }
    }
}
