package com.playgilround.schedule.client.signup.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.data.User;
import com.playgilround.schedule.client.signup.model.UserDataModel;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nispok.snackbar.Snackbar.with;

public class SignUpAdapter extends RecyclerView.Adapter<SignUpAdapter.RootViewHolder>
        implements UserDataModel {

    private static final String TAG = SignUpAdapter.class.getSimpleName();

    private static final int SIGN_UP_MAX = 6;

    public static final int TYPE_NAME = 0;
    public static final int TYPE_EMAIL = 1;
    public static final int TYPE_PASSWORD = 2;
    public static final int TYPE_REPEAT_PASSWORD = 3;
    public static final int TYPE_NICK_NAME = 4;
    public static final int TYPE_BIRTH = 5;

    private Context mContext;

    private int currentPosition = 0;

    private NameViewHolder mNameViewHolder;
    private EmailViewHolder mEmailViewHolder;
    private PasswordViewHolder mPasswordViewHolder;
    private RepeatPasswordViewHolder mRepeatPasswordViewHolder;
    private NickNameViewHolder mNickNameViewHolder;
    private BirthViewHolder mBirthViewHolder;

    private OnSignUpAdapterListener mOnSignUpAdapterListener = null;

    public SignUpAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view;
        switch (viewType) {
            case TYPE_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_name, parent, false);
                mNameViewHolder = new NameViewHolder(view);
                return mNameViewHolder;
            case TYPE_EMAIL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_email, parent, false);
                mEmailViewHolder = new EmailViewHolder(view);
                return mEmailViewHolder;
            case TYPE_PASSWORD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_password, parent, false);
                mPasswordViewHolder = new PasswordViewHolder(view);
                return mPasswordViewHolder;
            case TYPE_REPEAT_PASSWORD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_password_check, parent, false);
                mRepeatPasswordViewHolder = new RepeatPasswordViewHolder(view);
                return mRepeatPasswordViewHolder;
            case TYPE_NICK_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_nickname, parent, false);
                mNickNameViewHolder = new NickNameViewHolder(view);
                return mNickNameViewHolder;
            case TYPE_BIRTH:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_birth, parent, false);
                mBirthViewHolder = new BirthViewHolder(view);
                return mBirthViewHolder;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_up_empty, parent, false);
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

    public int getPosition() {
        return currentPosition;
    }

    public void setPosition(int position) {
        currentPosition = position;
    }

    public void setFocus() {
        switch (currentPosition) {
            case TYPE_NAME:
                mNameViewHolder.setFocus();
                break;
            case TYPE_EMAIL:
                mEmailViewHolder.setFocus();
                break;
            case TYPE_PASSWORD:
                mPasswordViewHolder.setFocus();
                break;
            case TYPE_REPEAT_PASSWORD:
                mRepeatPasswordViewHolder.setFocus();
                break;
            case TYPE_NICK_NAME:
                mNickNameViewHolder.setFocus();
                break;
            case TYPE_BIRTH:
                mBirthViewHolder.setFocus();
                mBirthViewHolder.hideKeyboard();
                break;
        }
    }

    public void setOnSignUpNextFieldListener(OnSignUpAdapterListener OnSignUpAdapterListener) {
        this.mOnSignUpAdapterListener = OnSignUpAdapterListener;
    }

    @Override
    public String getNameField() {
        return mNameViewHolder.getContent();
    }

    @Override
    public String getEmailField() {
        return mEmailViewHolder.getContent();
    }

    @Override
    public String getPasswordField() {
        return mPasswordViewHolder.getContent();
    }

    @Override
    public String getRepeatPasswordField() {
        return mRepeatPasswordViewHolder.getContent();
    }

    @Override
    public String getNicknameField() {
        return mNickNameViewHolder.getContent();
    }

    @Override
    public String getBirthField() {
        return mBirthViewHolder.getContent();
    }

    public void showSnackBar() {
        if (currentPosition < SIGN_UP_MAX - 1) {
            String[] errors = mContext.getResources().getStringArray(R.array.error_sign_up_field_check);

            SnackbarManager.show(with(mContext)
                    .type(SnackbarType.MULTI_LINE)
                    .actionLabel(mContext.getString(R.string.error_text_close))
                    .actionColorResource(R.color.error_snack_bar)
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    .text(errors[currentPosition]));
        }
    }

    private void dismissSnackBar() {
        SnackbarManager.dismiss();
    }

    abstract class RootViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.edit_sign_up)
        EditText mEditSignUp;

        protected int position;
        protected String content = null;

        EditText.OnEditorActionListener mOnEditorActionListener = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE &&
                        mOnSignUpAdapterListener != null) {
                    mOnSignUpAdapterListener.onNextField(position);
                    return true;
                }
                return false;
            }
        };

        RootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract boolean checkEditText(String content);

        public void bind(int position) {
            this.position = position;

            mEditSignUp.setOnEditorActionListener(mOnEditorActionListener);
            mEditSignUp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = mEditSignUp.getText().toString().trim();
                    if (checkEditText(text)) {
                        content = text;
                        mOnSignUpAdapterListener.ableNextButton();
                        dismissSnackBar();
                    } else {
                        mOnSignUpAdapterListener.disableNextButton();
                        dismissSnackBar();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        public String getContent() {
            return content;
        }

        void setFocus() {
            mEditSignUp.requestFocus();
        }

        void hideKeyboard() {
            InputMethodManager imm = (InputMethodManager) mEditSignUp.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditSignUp.getWindowToken(), 0);
        }

    }

    class NameViewHolder extends RootViewHolder {

        NameViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return content.length() > 0;
        }

    }

    class EmailViewHolder extends RootViewHolder {

        EmailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return User.checkEmail(content);
        }
    }

    class PasswordViewHolder extends RootViewHolder {

        PasswordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return User.checkPassWord(content);
        }

    }

    class RepeatPasswordViewHolder extends RootViewHolder {

        RepeatPasswordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return mPasswordViewHolder.getContent().equals(content);
        }

    }

    class NickNameViewHolder extends RootViewHolder {

        NickNameViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return true;
        }

    }

    class BirthViewHolder extends RootViewHolder {

        @BindView(R.id.datePicker_birth)
        DatePicker mBirth;

        BirthViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return content != null;
        }

        private void applyStyle(DatePicker datePicker) {
            Resources system = Resources.getSystem();
            int yearNumberPickerID = system.getIdentifier("year", "id", "android");
            int monthNumberPickerID = system.getIdentifier("month", "id", "android");
            int dayNumberPickerID = system.getIdentifier("day", "id", "android");

            NumberPicker yearNumberPicker = datePicker.findViewById(yearNumberPickerID);
            NumberPicker monthNumberPicker = datePicker.findViewById(monthNumberPickerID);
            NumberPicker dayNumberPicker = datePicker.findViewById(dayNumberPickerID);

            setDividerColor(yearNumberPicker);
            setDividerColor(monthNumberPicker);
            setDividerColor(dayNumberPicker);
        }

        private void setDividerColor(NumberPicker np) {
            if (np == null)
                return;

            final int count = np.getChildCount();
            for (int i = 0; i < count; i++) {
                View textView = np.getChildAt(i);
                try {
                    Field dividerField = np.getClass().getDeclaredField("mSelectionDivider");
                    Field textField = np.getClass().getDeclaredField("mSelectorWheelPaint");
                    dividerField.setAccessible(true);
                    textField.setAccessible(true);

                    ColorDrawable colorDrawable = new ColorDrawable(mContext.getResources().getColor(R.color.light_indigo));
                    dividerField.set(np, colorDrawable);

                    ((Paint) textField.get(np)).setColor(mContext.getResources().getColor(R.color.light_indigo));
                    ((EditText) textView).setTextColor(mContext.getResources().getColor(R.color.light_indigo));

                    np.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void bind(int position) {
            this.position = position;

            mBirth.init(mBirth.getYear(), mBirth.getMonth(), mBirth.getDayOfMonth(),
                    (view, year, monthOfYear, dayOfMonth) -> setBirth(year, monthOfYear + 1, dayOfMonth));
            applyStyle(mBirth);
        }

        void setBirth(int year, int month, int day) {
            content = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
        }
    }

    class EmptyViewHolder extends RootViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        boolean checkEditText(String content) {
            return false;
        }

    }

}
