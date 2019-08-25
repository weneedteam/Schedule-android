package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;
import com.playgilround.schedule.client.data.FriendList;
import com.playgilround.schedule.client.model.BaseResponse;

import io.reactivex.Single;
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
    Single<BaseResponse<FriendList>> friendList();

    //친구 요청
    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_REQUEST_FRIEND)
    Call<JsonObject> postFriendRequest(@Body JsonObject request);

    //친구 요청 여부 확인
    @Headers({"Accept: application/json"})
    @GET(BaseUrl.PATH_REQUEST_FRIEND + "{userId}")
    Call<JsonObject> getCheckRequest(@Path("userId") int userId);

    //친구 요청 취소
    @Headers({"Accept: application/json"})
    @DELETE(BaseUrl.PATH_REQUEST_FRIEND + "{userId}/")
    Call<JsonObject> deleteFriendRequest(@Path("userId") int userId);
}
