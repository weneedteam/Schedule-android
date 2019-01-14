package com.playgilround.schedule.client;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.playgilround.schedule.client.model.ScheduleMigration;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 18-12-27
 */
public class ScheduleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(getString(R.string.realm_name))
                .schemaVersion(3)
                .migration(new ScheduleMigration())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        RealmInspectorModulesProvider provider = RealmInspectorModulesProvider.builder(this)
                .withDeleteIfMigrationNeeded(true).build();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(provider)
                .build());

    }

  /*  @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/
}
