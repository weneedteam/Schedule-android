package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;
import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.signup.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @Headers({"Accept: application/json"})
    @GET(BaseUrl.PATH_USER_SEARCH)
    Call<JsonObject> searchUserName(@Query(BaseUrl.PARAM_USER_NAME_SEARCH) String name);

}
