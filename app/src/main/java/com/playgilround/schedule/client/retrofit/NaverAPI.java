package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface NaverAPI {

    @Headers({"Accept: application/json"})
    @GET("v1/nid/me")
    Call<JsonObject> naverProfile(@Header("Authorization") String token);
}
