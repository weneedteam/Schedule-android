package com.playgilround.schedule.client;

import android.app.Application;

import io.realm.Realm;

/**
 * 18-12-27
 */
public class ScheduleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
