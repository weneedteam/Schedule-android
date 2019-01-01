package com.playgilround.schedule.client;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.playgilround.schedule.client.activity.ScheCalendarActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] PERMISSION_STORAGE = {

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET

    };
    private Button btnCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("권한 요청을 해주세요.")
                .setPermissions(PERMISSION_STORAGE)
                .check();
        btnCalendar = (Button) findViewById(R.id.calendarBtn);
        btnCalendar.setOnClickListener(btnCalendar_listener);
    }

    //Show Calendar Activity
    private View.OnClickListener btnCalendar_listener = view -> {
        Intent calendarIntent = new Intent(getBaseContext(), ScheCalendarActivity.class);
        startActivity(calendarIntent);
    };

}

