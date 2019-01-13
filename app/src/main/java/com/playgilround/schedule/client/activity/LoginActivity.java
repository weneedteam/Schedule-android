package com.playgilround.schedule.client.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.playgilround.schedule.client.R;

import java.util.List;

/**
 * 18-01-10 로그인 화면
 */
public class LoginActivity extends Activity {

    private String[] PERMISSION_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage(getString(R.string.message_check_permission))
                .setPermissions(PERMISSION_STORAGE)
                .check();

        findViewById(R.id.btn_regist).setOnClickListener(l -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
        findViewById(R.id.btn_login).setOnClickListener(l ->
                startActivity(new Intent(this, MainActivity.class)));
    }

}

