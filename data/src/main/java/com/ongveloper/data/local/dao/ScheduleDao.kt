package com.ongveloper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ongveloper.data.local.dto.ScheduleEntity
import com.ongveloper.data.local.dto.ScheduleEntity.Companion.SCHEDULE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

//    @Query("SELECT * FROM $SCHEDULE_TABLE WHERE date like :date ORDER BY date DESC")
//    fun getSchedulesFlow(date: String): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM $SCHEDULE_TABLE ORDER BY id DESC")
    fun getSchedulesFlow(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(scheduleEntity: ScheduleEntity): Long

    /**
     * 삭제된 행의 수 return
     * */
    @Query("DELETE FROM $SCHEDULE_TABLE WHERE id = :id")
    suspend fun deleteSchedule(
        id: Long,
    ): Int
}