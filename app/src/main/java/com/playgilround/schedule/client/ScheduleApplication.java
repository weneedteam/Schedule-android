package com.playgilround.schedule.client;

import android.app.Application;

import com.playgilround.schedule.client.model.ScheduleMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 18-12-27
 */
public class ScheduleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(getString(R.string.realm_name))
                .schemaVersion(0)
                .migration(new ScheduleMigration())
                .build();

        Realm.init(this);
        Realm.setDefaultConfiguration(config);
    }
}
