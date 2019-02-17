package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;
import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.signup.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_SIGN_UP)
    Call<ResponseMessage> signUp(@Body User user);

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_EMAIL_SIGN_IN)
    Call<JsonObject> emailSignIn(@Body User user);

    /*@Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_TOKEN_SIGN_IN)
    Call<JsonObject> tokenSignIn(@Body User user);*/

}
