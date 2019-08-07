package com.playgilround.schedule.client.di

import android.content.Context
import com.playgilround.schedule.client.data.repository.UsersRepository
import com.playgilround.schedule.client.data.source.local.UsersLocalDataSource
import com.playgilround.schedule.client.data.source.network.UsersRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 19-08-07
 * User 관련 Module
 */
@Module
class UserModule {

    @Provides
    @Singleton
    fun provideUsersRepository(context: Context): UsersRepository {
        return UsersRepository(UsersLocalDataSource.getInstance(context),
                                UsersRemoteDataSource.getInstance(context))
    }
}