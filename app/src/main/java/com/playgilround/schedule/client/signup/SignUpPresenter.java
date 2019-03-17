package com.playgilround.schedule.client.signup;

import com.crashlytics.android.core.CrashlyticsCore;
import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.UserAPI;
import com.playgilround.schedule.client.signup.model.User;
import com.playgilround.schedule.client.signup.model.UserDataModel;
import com.playgilround.schedule.client.signup.view.SignUpAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpPresenter implements SignUpContract.Presenter {

    private final SignUpContract.View mView;
    private final UserDataModel mUserDataModel;

    static final int ERROR_SIGN_UP = 0x0001;
    static final int ERROR_NETWORK_CUSTOM = 0x0002;

    private User mUser;

    SignUpPresenter(SignUpContract.View view, UserDataModel userDataModel) {
        mView = view;
        mView.setPresenter(this);

        mUser = new User();
        mUserDataModel = userDataModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void onClickNext(int position) {
        boolean check = false;
        switch (position) {
            case SignUpAdapter.TYPE_NAME:
                mUser.setUsername(mUserDataModel.getNameField());
                check = mUser.getUsername() != null;
                break;
            case SignUpAdapter.TYPE_EMAIL:
                mUser.setEmail(mUserDataModel.getEmailField());
                check = mUser.getEmail() != null;
                break;
            case SignUpAdapter.TYPE_PASSWORD:
                mUser.setPassword(mUserDataModel.getPasswordField());
                check = mUser.getPassword() != null;
                break;
            case SignUpAdapter.TYPE_REPEAT_PASSWORD:
                mUser.setPassword2(mUserDataModel.getRepeatPasswordField());
                check = mUser.getPassword2() != null;
                break;
            case SignUpAdapter.TYPE_NICK_NAME:
                mUser.setNickname(mUserDataModel.getNicknameField());
                check = mUser.getNickname() != null;
                break;
            case SignUpAdapter.TYPE_BIRTH:
                mUser.setBirth(mUserDataModel.getBirthField());
                check = mUser.getBirth() != null;
                break;
        }
        mView.fieldCheck(check);
    }

    @Override
    public void onClickBack(int position) {
        switch (position) {
            case SignUpAdapter.TYPE_NAME:
                mUser.setUsername(null);
                break;
            case SignUpAdapter.TYPE_EMAIL:
                mUser.setEmail(null);
                break;
            case SignUpAdapter.TYPE_PASSWORD:
                mUser.setPassword(null);
                break;
            case SignUpAdapter.TYPE_REPEAT_PASSWORD:
                mUser.setPassword2(null);
                break;
            case SignUpAdapter.TYPE_NICK_NAME:
                mUser.setNickname(null);
                break;
            case SignUpAdapter.TYPE_BIRTH:
                mUser.setBirth(null);
                break;
        }
    }

    @Override
    public void signUp() {

        Retrofit retrofit = APIClient.getLoggingClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        mUser.setPassword(User.base64Encoding(mUser.getPassword()));
        mUser.setPassword2(User.base64Encoding(mUser.getPassword2()));

        userAPI.signUp(mUser).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mView.singUpComplete();
                } else {
                    CrashlyticsCore.getInstance().log(response.toString());
                    mView.signUpError(ERROR_SIGN_UP);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                CrashlyticsCore.getInstance().log(t.toString());
                mView.signUpError(ERROR_NETWORK_CUSTOM);
            }
        });

    }
}
