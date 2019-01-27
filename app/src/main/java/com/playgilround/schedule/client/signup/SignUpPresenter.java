package com.playgilround.schedule.client.signup;

import android.util.Log;

import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.model.User;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpPresenter implements SignUpContract.Presenter {

    private final SignUpContract.View mView;

    SignUpPresenter(SignUpContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void signUp(User user) {

        Retrofit retrofit = APIClient.getClient();
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
        });

    }
}
