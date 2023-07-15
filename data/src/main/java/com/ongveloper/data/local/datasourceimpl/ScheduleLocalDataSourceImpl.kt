package com.ongveloper.data.local.datasourceimpl

import com.ongveloper.data.local.dao.ScheduleDao
import com.ongveloper.data.local.datasource.ScheduleLocalDataSource
import com.ongveloper.data.local.dto.ScheduleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleLocalDataSourceImpl @Inject constructor(
    private val scheduleDao: ScheduleDao,
) : ScheduleLocalDataSource {

    override fun getSchedulesFlow(): Flow<List<ScheduleEntity>> =
        scheduleDao.getSchedulesFlow()


    override suspend fun insertSchedule(scheduleEntity: ScheduleEntity): Int =
        scheduleDao.insertSchedule(scheduleEntity)


    override suspend fun deleteSchedule(id: Long): Int =
        scheduleDao.deleteSchedule(id)
}