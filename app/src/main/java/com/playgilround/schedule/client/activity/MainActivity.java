package com.playgilround.schedule.client.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.playgilround.schedule.client.R;
import com.playgilround.schedule.client.schedule.ScheduleCalendarActivity;
import com.playgilround.schedule.client.model.User;
import com.playgilround.schedule.client.retrofit.APIClient;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String[] PERMISSION_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_test).setOnClickListener(v ->
            startActivity(new Intent(MainActivity.this, ScheduleCalendarActivity.class)));

        findViewById(R.id.button_calendar).setOnClickListener(v -> {

            User user = new User();
            user.setUserName("JoungSik");
            user.setNickName("JoungSik");
            user.setEmail("tjstlr2010@gmail.com");
            user.setBirth("1995/08/18");
            user.setPassword("js30211717");
            user.setLanguage("KR");
            user.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJ1c2VybmFtZSI6InRqc3RscjIwMTBAZ21haWwuY29tIiwiZXhwIjoxNTQ4MDAyNzg4LCJlbWFpbCI6InRqc3RscjIwMTBAZ21haWwuY29tIiwib3JpZ19pYXQiOjE1NDc3NDM1ODh9.7fEpdjQlN7_uZWFco_9wr7U3gcEX06wchlMZHu-VUZY");

            /*HashMap<String, String> user = new HashMap<>();
            user.put("email", "tjstlr2010@gmail.com");
            user.put("password", "js30211717");*/

            /*JsonObject user = new JsonObject();
            user.addProperty("email", "tjstlr2010@gmail.com");
            user.addProperty("password", "js30211717");*/

            Retrofit retrofit = APIClient.getLoggingClient();
            /*UserAPI userAPI = retrofit.create(UserAPI.class);
            userAPI.tokenSignIn(user).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        Log.v(TAG, "result - " + jsonObject.toString());
                        String token = new Gson().fromJson(jsonObject.get("token"), String.class);
                        Log.v(TAG, "token - " + token);
                    } else {
                        Log.e(TAG, response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });*/

            /*HolidayAPI holidayAPI = retrofit.create(HolidayAPI.class);
            holidayAPI.holidays().enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonArray jsonArray  = response.body().getAsJsonArray("holidays");
                        Holiday[] holidays = new Gson().fromJson(jsonArray, Holiday[].class);
                        for (Holiday holiday : holidays) {
                            Log.v(TAG, "holiday - " + holiday.getName());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });*/
        });
    }
}


