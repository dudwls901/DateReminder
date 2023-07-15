package com.ongveloper.data.local.datasource

import com.ongveloper.data.local.dto.ScheduleEntity
import kotlinx.coroutines.flow.Flow

interface ScheduleLocalDataSource {

    fun getSchedulesFlow(): Flow<List<ScheduleEntity>>

    suspend fun insertSchedule(scheduleEntity: ScheduleEntity): Int

    suspend fun deleteSchedule(id: Long): Int

}