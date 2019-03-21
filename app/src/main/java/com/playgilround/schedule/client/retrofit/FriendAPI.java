package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FriendAPI {

    //친구 리스트 확인
    @Headers({"Accept: application/json"})
    @GET(BaseUrl.PATH_REQUEST_FRIEND)
    Call<JsonObject> getFriendList();

    //친구 요청
    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_REQUEST_FRIEND)
    Call<JsonObject> postFriendRequest(@Body JsonObject request);

    //친구 요청 여부 확인
    @Headers({"Accept: application/json"})
    @GET(BaseUrl.PATH_CHECK_REQUEST_FRIEND)
    Call<JsonObject> getCheckRequest(@Path("userId") int userId);

    //친구 요청 취소
    @Headers({"Accept: application/json"})
    @DELETE(BaseUrl.PATH_CANCEL_REQUEST_FRIEND)
    Call<JsonObject> deleteFriendRequest(@Path("userId") int userId);
}
