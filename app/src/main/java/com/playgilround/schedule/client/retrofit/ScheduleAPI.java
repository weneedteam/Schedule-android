package com.playgilround.schedule.client.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ScheduleAPI {

    @Headers({"Accept: application/json"})
    @POST(BaseUrl.PATH_NEW_SCHEDULE)
    Call<JsonObject> newSchedule(@Body JsonObject schedule);
}
