package com.ongveloper.data.local.datasource

import com.ongveloper.data.local.dto.ScheduleEntity
import kotlinx.coroutines.flow.Flow

interface ScheduleLocalDataSource {

    suspend fun getSchedule(id: Long): ScheduleEntity

    fun getSchedulesFlow(): Flow<List<ScheduleEntity>>

    suspend fun insertSchedule(scheduleEntity: ScheduleEntity): Long

    suspend fun deleteSchedule(id: Long): Int
}