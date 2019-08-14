package com.playgilround.schedule.client

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.google.firebase.messaging.FirebaseMessaging
import com.playgilround.schedule.client.di.AppComponent
import com.playgilround.schedule.client.di.AppModule
import com.playgilround.schedule.client.di.DaggerAppComponent
import com.playgilround.schedule.client.model.ScheduleMigration
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration

class ScheduleApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name(getString(R.string.realm_name))
                .schemaVersion(3)
                .migration(ScheduleMigration())
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)

        val provider = RealmInspectorModulesProvider.builder(this)
                .withDeleteIfMigrationNeeded(true)
                .build()

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(provider)
                        .build())

        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        initComponent()
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
    companion object {
        fun get(context: Context): ScheduleApplication {
            return context.applicationContext as ScheduleApplication
        }
    }
}