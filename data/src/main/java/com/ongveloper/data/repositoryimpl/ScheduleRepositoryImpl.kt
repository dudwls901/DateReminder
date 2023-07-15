package com.ongveloper.data.repositoryimpl

import com.ongveloper.data.local.datasource.ScheduleLocalDataSource
import com.ongveloper.data.local.dto.toEntity
import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleLocalDataSource: ScheduleLocalDataSource,
) : ScheduleRepository {

    override fun getSchedulesFlow(): Flow<List<Schedule>> =
        scheduleLocalDataSource.getSchedulesFlow().map { schedules ->
            schedules.map {
                it.toDomainModel()
            }
        }

    override suspend fun insertSchedule(schedule: Schedule): Int =
        scheduleLocalDataSource.insertSchedule(schedule.toEntity())

    override suspend fun deleteSchedule(id: Long): Int =
        scheduleLocalDataSource.deleteSchedule(id)
}