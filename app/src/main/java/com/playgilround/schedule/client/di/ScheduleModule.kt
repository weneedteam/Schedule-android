package com.playgilround.schedule.client.di

import com.playgilround.schedule.client.data.ScheduleData
import com.playgilround.schedule.client.data.repository.ScheduleRepository
import com.playgilround.schedule.client.data.source.local.ScheduleLocalDataSource
import com.playgilround.schedule.client.data.source.network.ScheduleRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 19-08-13
 * ScheduleData Module
 */
@Module
class ScheduleModule {

    @Provides
    @Singleton
    fun provideScheduleRepository(scheduleLocalDataSource: ScheduleLocalDataSource, scheduleRemoteDataSource: ScheduleRemoteDataSource): ScheduleRepository {
        return ScheduleRepository(scheduleLocalDataSource, scheduleRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSchedule(): ScheduleData {
        return ScheduleData()
    }
}