package com.playgilround.schedule.client.signin;

import android.util.Log;

import com.google.gson.JsonObject;
import com.playgilround.schedule.client.signup.model.User;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInPresenter implements SignInContract.Presenter {

    private final SignInContract.View mView;

    SignInPresenter(SignInContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void signIn(User user) {

        Retrofit retrofit = APIClient.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        userAPI.emailSignIn(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("SignInPresenter", response.body().toString());
                    mView.signInComplete();
                } else {
                    Log.e("SignInPresenter", response.body().toString());
                    mView.signInError();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("SignInPresenter", t.toString());
                mView.signInError();
            }
        });

    }
}
