package com.playgilround.schedule.client.friend;

import android.content.Context;

import com.crashlytics.android.core.CrashlyticsCore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.UserAPI;
import com.playgilround.schedule.client.signup.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 19-03-14
 * 친구 관련 데이터 처리
 */
public class FriendPresenter implements FriendContract.Presenter {

    private final FriendContract.View mView;
    private final Context mContext;

    static final int ERROR_NETWORK_CUSTOM = 0x0001;

    FriendPresenter(Context context, FriendContract.View view) {
        mView = view;
        mContext = context;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onSearch(String name) {

        Retrofit retrofit = APIClient.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        userAPI.searchUserName(name).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = new Gson().fromJson(response.body().get("user"), User.class);
                    mView.searchResult(user);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CrashlyticsCore.getInstance().log(t.toString());
                mView.searchError(ERROR_NETWORK_CUSTOM);
            }
        });

    }
}
