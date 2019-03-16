package com.playgilround.schedule.client.signup;

import com.playgilround.schedule.client.signup.view.SignUpAdapter;
import com.playgilround.schedule.client.signup.model.User;
import com.playgilround.schedule.client.signup.model.UserDataModel;

public class SignUpPresenter implements SignUpContract.Presenter {

    private final SignUpContract.View mView;
    private final UserDataModel mUserDataModel;

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
    public void signUp(User user) {

        /*Retrofit retrofit = APIClient.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        userAPI.signUp(user).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Log.v("SignUpPresenter", response.body().toString());
                    mView.singUpComplete();
                } else {
                    Log.e("SignUpPresenter", response.body().toString());
                    mView.signUpError();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e("SignUpPresenter", t.toString());
                mView.signUpError();
            }
        });*/

    }
}
