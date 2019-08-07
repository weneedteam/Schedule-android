package com.playgilround.schedule.client.di

import com.playgilround.schedule.client.ScheduleApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
public interface AppComponent {
    fun inject(app: ScheduleApplication)
}

