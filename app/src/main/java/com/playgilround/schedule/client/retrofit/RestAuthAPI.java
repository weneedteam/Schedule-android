package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestAuthAPI {

    // facebook login
    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_FACEBOOK_SIGN_IN)
    Call<JsonObject> postFacebookLogin(@Body String token);

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_NAVER_SIGN_IN)
    Call<JsonObject> postNaverLogin(@Body String token);

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_KAKAO_SIGN_IN)
    Call<JsonObject> postKakaoLogin(@Body String token);

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_GOOGLE_SIGN_IN)
    Call<JsonObject> postGoogleLogin(@Body String token);
}
