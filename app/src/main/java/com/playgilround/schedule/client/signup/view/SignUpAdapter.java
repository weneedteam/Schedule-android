package com.playgilround.schedule.client.signup.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.signup.SignUpAdapterContract;
import com.playgilround.schedule.client.signup.SignUpAdapterPresenter;
import com.playgilround.schedule.client.signup.model.User;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.joda.time.DateTime;

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
public class SignUpAdapter extends RecyclerView.Adapter<SignUpAdapter.RootViewHolder> implements SignUpAdapterContract.View {

    private static final String TAG = SignUpAdapter.class.getSimpleName();

    private static final int SIGN_UP_MAX = 6;

    private static final int TYPE_NAME = 0;
    private static final int TYPE_EMAIL = 1;
    private static final int TYPE_PASSWORD = 2;
    private static final int TYPE_REPEAT_PASSWORD = 3;
    private static final int TYPE_NICK_NAME = 4;
    private static final int TYPE_BIRTH = 5;

    private SignUpAdapterContract.Presenter mPresenter;
    private Context mContext;

    private String password;
    private String strName;
    private String strEmail;
    private String strPw;
    private String strNickName;

    private int retPosition = 0;
    private OnButtonClick mCallback;

    public SignUpAdapter(Context context, OnButtonClick listener) {
        new SignUpAdapterPresenter(this);
        mContext = context;
        mCallback = listener;
    }

    @NonNull
    @Override
    public RootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new NameViewHolder(view);
            case TYPE_EMAIL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new EmailViewHolder(view);
            case TYPE_PASSWORD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new PasswordViewHolder(view);
            case TYPE_REPEAT_PASSWORD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new RepeatPasswordViewHolder(view);
            case TYPE_NICK_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new NickNameViewHolder(view);
            case TYPE_BIRTH:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new BirthViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signup, parent, false);
                return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RootViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return SIGN_UP_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void setPresenter(SignUpAdapterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    abstract class RootViewHolder extends RecyclerView.ViewHolder {

        RootViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(int position);

    }

    class NameViewHolder extends RootViewHolder {

        NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class EmailViewHolder extends RootViewHolder {

        EmailViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class PasswordViewHolder extends RootViewHolder {

        PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class RepeatPasswordViewHolder extends RootViewHolder {

        RepeatPasswordViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class NickNameViewHolder extends RootViewHolder {

        NickNameViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class BirthViewHolder extends RootViewHolder {

        BirthViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class EmptyViewHolder extends RootViewHolder {

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(int position) {

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements DatePickerDialog.OnDateSetListener {

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

        @BindView(R.id.ivBackBtn)
        ImageView ivBackBtn;

        String snackContent;

        String strContent;

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

            //닉네임 쪽만 왜 이름부분에서 적었던 텍스트가 보이는 지?
            if (position == 4) {
                etContent.setText("");
            }
            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    strContent = etContent.getText().toString().trim();
                    switch (position) {
                        case 0:
                            //이름은 2글자 이상
                            if (strContent.length() > 1) {
                                strName = strContent;
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                //다음 버튼 비활성화 아이콘 디자이너한테 받아야 함.
                                ivNext.setImageResource(R.mipmap.nav_card);
                                showSnackbar(snackContent);
                            }
                            break;
                        case 1:
                            //이메일 형식
                            if (checkEmail(strContent)) {
                                strEmail = strContent;
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                ivNext.setImageResource(R.mipmap.nav_card);
                                showSnackbar(snackContent);
                            }
                            break;
                        case 2:
                            //비밀번호 형식
                            if (checkPassWord(strContent)) {
                                password = strContent;
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                ivNext.setImageResource(R.mipmap.nav_card);
                                showSnackbar(snackContent);
                            }
                            break;
                        case 3:
                            //비밀번호 전과 같은지 비교
                            if (password.equals(strContent)) {
                                //추후에 암호화
                                strPw = strContent;
                                ivNext.setImageResource(R.mipmap.next_btn);
                                dismissSnackbar();
                            } else {
                                ivNext.setImageResource(R.mipmap.nav_card);
                                showSnackbar(snackContent);
                            }
                            break;
                        case 4:
                            //닉네임 중복 확인
                            if (strContent.equals("")) {
                                ivNext.setImageResource(R.mipmap.next_btn);
//                                showSnackbar(snackContent);
                            } else {
                                strNickName = strContent;
                                ivNext.setImageResource(R.mipmap.next_btn);
//                                dismissSnackbar();
                            }
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

        @OnClick(R.id.ivNext)
        void onNextClick() {
            retPosition = retPosition + 1;
            if (retPosition == 5) {
                tvTitle.setText("생일을 설정해주세요.");
                tvContent.setVisibility(View.GONE);
                etContent.setVisibility(View.GONE);
                DateTime dateTime = new DateTime();
                int year = dateTime.getYear();
                int month = dateTime.getMonthOfYear() - 1;
                int day = dateTime.getDayOfMonth();
                showBirthDialog(year, month, day, R.style.birthDatePicker);
            } else {
                if (ivNext.getDrawable().getConstantState() == ivNext.getResources().getDrawable(R.mipmap.next_btn).getConstantState()) {
                    mCallback.onNextClick(retPosition);
                }
            }
        }

        @OnClick(R.id.ivBackBtn)
        void onBackButton() {
            retPosition = retPosition -1;
            mCallback.onBackClick(retPosition);
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

        void showBirthDialog(int year, int month, int day, int spinnerTheme) {
            new SpinnerDatePickerDialogBuilder()
                    .context(mContext)
                    .callback(this)
                    .spinnerTheme(spinnerTheme)
                    .defaultDate(year, month, day)
                    .build()
                    .show();
        }

        @Override
        public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int month, int day) {
            DateTime dateTime = new DateTime(year, month + 1, day, 0, 0);

            String strBirth = dateTime.toString(mContext.getString(R.string.text_date_year_month_day));
            Log.d(TAG, "Result ->" + strName + "//" + strEmail + "//" + strPw + "//" + strNickName + "//" + strBirth);

            User user = new User();

            user.setUserName(strName);
            user.setEmail(strEmail);
            user.setPassword(strPw);
            user.setNickName(strNickName);
            user.setBirth(strBirth);

            mCallback.onNextClick(SIGN_UP_MAX);
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
            String valiPass = "^(?=.*[a-z])(?=.*[0-9]).{8,}$";

            Pattern pattern = Pattern.compile(valiPass);
            Matcher matcher = pattern.matcher(password);

            return matcher.matches();
        }

    }

    public interface OnButtonClick {
        void onNextClick(int position);
        void onBackClick(int position);
    }
}
