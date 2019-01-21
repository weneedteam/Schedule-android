package com.playgilround.schedule.client.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.playgilround.schedule.client.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

        ButterKnife.bind(this);
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


        //});

    }

    @OnClick(R.id.btn_regist)
    void onRegistClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        startActivity(new Intent(this, MainActivity.class));
    }
    /*@OnClick(R.id.btn_regist)
    void onRegistClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        startActivity(new Intent(this, MainActivity.class));
    }*/

}

