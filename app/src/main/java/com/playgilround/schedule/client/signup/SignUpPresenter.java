package com.playgilround.schedule.client.signup;

import android.util.Log;

import com.google.gson.JsonObject;
import com.playgilround.schedule.client.data.User;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.UserAPI;
import com.playgilround.schedule.client.signup.model.UserDataModel;
import com.playgilround.schedule.client.signup.view.SignUpAdapter;

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

        Log.v(SignUpPresenter.class.getSimpleName(), "SignUp");

        Retrofit retrofit = APIClient.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        mUser.setPassword(User.base64Encoding(mUser.getPassword()));
        mUser.setPassword2(User.base64Encoding(mUser.getPassword2()));

        // Todo:: 나중에 시간이 나면 SignUp Request Body 의 구현체를 User.class 형태로 바꾸기
        JsonObject jsonObject = new JsonObject();

        JsonObject user = new JsonObject();
        user.addProperty("username", mUser.getUsername());
        user.addProperty("nickname", mUser.getNickname());
        user.addProperty("email", mUser.getEmail());
        user.addProperty("password", mUser.getPassword());
        user.addProperty("password2", mUser.getPassword2());

        jsonObject.add("user", user);
        jsonObject.addProperty("birth", mUser.getBirth());
        jsonObject.addProperty("language", mUser.getLanguage());

        mView.signUpComplete();

        /*userAPI.signUp(jsonObject).enqueue(new Callback<ResponseMessage>() {
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
        });*/

    }
}
