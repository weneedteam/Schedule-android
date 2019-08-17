package com.playgilround.schedule.client.di

import com.playgilround.schedule.client.data.repository.FriendRepository
import com.playgilround.schedule.client.data.source.network.FriendRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 19-08-17
 * Friend 관련 Module
 */
@Module
class FriendModule {

    @Provides
    @Singleton
    fun provideFriendRepository(friendRemoteDataSource: FriendRemoteDataSource): FriendRepository {
        return FriendRepository(friendRemoteDataSource)
    }
}