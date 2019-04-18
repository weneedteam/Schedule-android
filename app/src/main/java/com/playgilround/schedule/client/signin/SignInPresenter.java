package com.playgilround.schedule.client.signin;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.playgilround.schedule.client.data.User;
import com.playgilround.schedule.client.retrofit.APIClient;
import com.playgilround.schedule.client.retrofit.BaseUrl;
import com.playgilround.schedule.client.retrofit.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class SignInPresenter implements SignInContract.Presenter {

    private static final String TAG = SignInPresenter.class.getSimpleName();
    private static final String PREF_NAME = TAG + "_user.pref";
    private static final String PREF_KEY_USER = TAG + "_user";

    static final int ERROR_EMAIL = 0x0001;
    static final int ERROR_PASSWORD = 0x0002;
    static final int ERROR_FAIL_SIGN_IN = 0x0003;
    static final int ERROR_NETWORK_CUSTOM = 0x0004;

    private final SignInContract.View mView;
    private final Context mContext;

    private User mUser = null;

    SignInPresenter(Context context, SignInContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mContext = context;
    }

    @Override
    public void start() {

    }

    private void saveUser(String user) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_USER, user);
        editor.apply();
    }

    @Override
    public boolean checkAutoSignIn() {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userString = pref.getString(PREF_KEY_USER, "");

        mUser = new Gson().fromJson(userString, User.class);
        return mUser != null;
    }

    @Override
    public void autoSignIn() {
        mView.showProgressBar();

        Retrofit retrofit = APIClient.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_TOKEN, mUser.getToken());

        userAPI.tokenSignIn(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject userInfo = response.body().getAsJsonObject("user_info");
                    User user = new Gson().fromJson(userInfo.get("user"), User.class);

                    String birth = userInfo.get("birth").getAsString();
                    String language = userInfo.get("language").getAsString();
                    String token = userInfo.get("token").getAsString();

                    user.setBirth(birth);
                    user.setLanguage(language);
                    user.setToken(token);

                    saveUser(new Gson().toJson(user));

                    mView.signInComplete();
                } else {
                    mView.signInError(ERROR_FAIL_SIGN_IN);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // CrashlyticsCore.getInstance().log(t.toString());
                mView.signInError(ERROR_NETWORK_CUSTOM);
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        mView.showProgressBar();

        if (!User.checkEmail(email)) {
            mView.signInError(ERROR_EMAIL);
            return;
        }

        if (password.isEmpty()) {
            mView.signInError(ERROR_PASSWORD);
            return;
        }

        Retrofit retrofit = APIClient.getClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_EMAIL, email);

        // Todo:: Server 측에 base64 encoding 제안
        // jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_PASSWORD, User.base64Encoding(password));
        jsonObject.addProperty(BaseUrl.PARAM_SIGN_IN_PASSWORD, password);

        userAPI.emailSignIn(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject userInfo = response.body().getAsJsonObject("user_info");
                    User user = new Gson().fromJson(userInfo.get("user"), User.class);

                    String birth = userInfo.get("birth").getAsString();
                    String language = userInfo.get("language").getAsString();
                    String token = userInfo.get("token").getAsString();

                    user.setBirth(birth);
                    user.setLanguage(language);
                    user.setToken(token);

                    saveUser(new Gson().toJson(user));

                    mView.signInComplete();
                } else {
                    mView.signInError(ERROR_FAIL_SIGN_IN);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // CrashlyticsCore.getInstance().log(t.toString());
                mView.signInError(ERROR_NETWORK_CUSTOM);
            }
        });
    }
}
