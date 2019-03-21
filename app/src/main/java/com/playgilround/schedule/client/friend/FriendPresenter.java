package com.playgilround.schedule.client.friend;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.core.CrashlyticsCore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.FriendAPI;
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
    static final int FAIL_USER_FOUND = 0x0002;

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
                    if (user != null) {
                        mView.searchFind(user);
                    } else {
                        mView.searchError(FAIL_USER_FOUND);
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CrashlyticsCore.getInstance().log(t.toString());
                mView.searchError(ERROR_NETWORK_CUSTOM);
            }
        });
    }

    @Override
    public void onCheckFriend(User result) {

        Retrofit retrofit = APIClient.getClient();
        FriendAPI friendAPI = retrofit.create(FriendAPI.class);
        friendAPI.getCheckRequest(0).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //이미 친구 인지, 친구가 아닌지, 친구 요청중인지 판단 후 처리
                } else {
                    mView.onCheckResult(result);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}
