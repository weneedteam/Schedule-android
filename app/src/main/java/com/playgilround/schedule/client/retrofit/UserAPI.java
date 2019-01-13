package com.playgilround.schedule.client.retrofit;

import com.playgilround.schedule.client.model.ResponseMessage;
import com.playgilround.schedule.client.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_SIGN_UP)
    Call<ResponseMessage> signUp(@Body User user);

}
