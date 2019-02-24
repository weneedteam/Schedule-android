package com.playgilround.schedule.client.signup.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.playgilround.schedule.client.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nispok.snackbar.Snackbar.SnackbarDuration;
import static com.nispok.snackbar.Snackbar.with;

/**
 * 19-02-23
 * SignUp Adapter
 */
public class SignUpAdapter extends RecyclerView.Adapter<SignUpAdapter.ViewHolder> {

    private Context mContext;

    private static final String TAG = SignUpAdapter.class.getSimpleName();

    private static String password;
    public SignUpAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_signup, parent, false));
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

        @BindView(R.id.etSignUpContent)
        EditText etContent;

        @BindView(R.id.ivNext)
        ImageView ivNext;

        @BindView(R.id.datePicker)
        DatePicker datePicker;

        String snackContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            String[] titles = mContext.getResources().getStringArray(R.array.signup_text_title_array);
            String title = titles[position];

            String[] contents = mContext.getResources().getStringArray(R.array.signup_text_content_array);
            String content = contents[position];

            String[] snackContents = mContext.getResources().getStringArray(R.array.signup_text_snackbar);
            snackContent = snackContents[position];

            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    switch (position) {
                        case 0:
                            //이름은 2글자 이상
                            if (etContent.getText().toString().trim().length() > 1) {
                                //Todo:: 다음 버튼 활성화
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                //다음 버튼 비활성화 아이콘 디자이너한테 받아야 함.
                                showSnackbar(snackContent);
                            }
                            break;
                        case 1:
                            //이메일 형식
                            if (checkEmail(etContent.getText().toString().trim())) {
                                //Todo:: 다음 버튼 활성화
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                showSnackbar(snackContent);
                            }
                            break;
                        case 2:
                            //비밀번호 형식
                            if (checkPassWord(etContent.getText().toString().trim())) {
                                //Todo:: 다음 버튼 활성화
                                password = etContent.getText().toString().trim();
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                showSnackbar(snackContent);
                            }
                            break;
                        case 3:
                            //비밀번호 전과 같은지 비교
                            if (password.equals(etContent.getText().toString().trim())) {
                                //Todo:: 다음 버튼 활성화
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                showSnackbar(snackContent);
                            }
                            break;
                        case 4:
                            //닉네임 중복 확인
                            ivNext.setImageResource(R.mipmap.next_btn);
                            showSnackbar(snackContent);
                            break;
                        case 5:
                            //생년월일 설정
                            Log.d(TAG, "show datePicker");
                            etContent.setVisibility(View.GONE);
                            datePicker.setVisibility(View.VISIBLE);
                            break;

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tvTitle.setText(title);
            tvContent.setText(content);
            mProgress.setProgress(position + 1);
        }

        void showSnackbar(String snack) {
            SnackbarManager.show(
                    with(mContext)
                            .type(SnackbarType.MULTI_LINE)
                            .actionLabel("Close")
                            .actionColor(Color.parseColor("#FF8A80"))
                            .duration(SnackbarDuration.LENGTH_INDEFINITE)
                            .text(snack));
        }

        void dismissSnackbar() {
            SnackbarManager.dismiss();
        }

        /**
         * 이메일 형식 체크
         */
        private boolean checkEmail(String email) {
            String mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
            Pattern p = Pattern.compile(mail);
            Matcher m = p.matcher(email);
            return m.matches();
        }

        /**
         * 패스워드 유효성검사
         * 영문, 숫자입력
         * 정규식 (영문, 숫자 8자리 이상)
         */
        private boolean checkPassWord(String password) {
            String valiPass =  "^(?=.*[a-z]).{8,}$";

            Pattern pattern = Pattern.compile(valiPass);
            Matcher matcher = pattern.matcher(password);

            return matcher.matches();
        }

        @OnClick(R.id.ivNext)
        void onButtonClick() {

        }
    }

}
