package com.playgilround.schedule.client.data.repository

import com.playgilround.schedule.client.data.source.ScheduleDataSource
import com.playgilround.schedule.client.data.source.network.ScheduleRemoteDataSource

class ScheduleRepository(
        private val scheduleLocalDataSource: ScheduleDataSource,
        private val scheduleRemoteDataSource: ScheduleRemoteDataSource) : ScheduleDataSource {
}
