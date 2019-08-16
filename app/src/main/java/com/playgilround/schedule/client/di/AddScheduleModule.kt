package com.playgilround.schedule.client.di

import com.playgilround.schedule.client.addschedule.TestAddScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AddScheduleModule {

    @ContributesAndroidInjector
    fun addScheduleFragment(): TestAddScheduleFragment
}