package com.ongveloper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ongveloper.data.local.dao.ScheduleDao
import com.ongveloper.data.local.dto.AlarmTimesTypeConverter
import com.ongveloper.data.local.dto.ScheduleEntity

const val SCHEDULE_DB_NAME = "SCHEDULE_DB"

@Database(
    entities = [ScheduleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AlarmTimesTypeConverter::class)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}