package com.ongveloper.domain.repository

import com.ongveloper.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun getSchedule(id: Long): Schedule

    fun getSchedulesFlow(): Flow<List<Schedule>>

    suspend fun insertSchedule(schedule: Schedule): Long

    suspend fun deleteSchedule(id: Long): Int
}