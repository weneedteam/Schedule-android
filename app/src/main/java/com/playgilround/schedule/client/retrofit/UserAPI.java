package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;
import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.signup.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface UserAPI {

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_SIGN_UP)
    Call<ResponseMessage> signUp(@Body JsonObject user);

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_EMAIL_SIGN_IN)
    Call<User> emailSignIn(@Body JsonObject user);

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_TOKEN_SIGN_IN)
    Call<User> tokenSignIn(@Body JsonObject token);

}
