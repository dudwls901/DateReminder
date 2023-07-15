package com.ongveloper.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.ongveloper.data.local.dto.ScheduleEntity.Companion.SCHEDULE_TABLE
import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.model.Schedule.Companion.mapLocalDateTime
import com.ongveloper.domain.model.Schedule.Companion.mapTimeMillis
import com.ongveloper.domain.model.ScheduleParams
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

@Entity(
    tableName = SCHEDULE_TABLE,
)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val scheduleParams: ScheduleParams,
    val diffMinutes: Long,
    val alarmTimes: List<Long>,
    val isFinished: Boolean,
) {
    fun toDomainModel(): Schedule = Schedule(
        id,
        scheduleParams,
        diffMinutes,
        alarmTimes.mapLocalDateTime(),
        isFinished,
    )

    companion object {
        const val SCHEDULE_TABLE = "SCHEDULE"
    }
}

fun Schedule.toEntity(): ScheduleEntity = ScheduleEntity(
    id,
    scheduleParams,
    diffMinutes,
    alarmTimes.mapTimeMillis(),
    isFinished,
)

class AlarmTimesTypeConverter {

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @TypeConverter
    fun jsonToList(value: String): List<Long> {
        return json.decodeFromString(ListSerializer(Long.serializer()), value)
    }

    @TypeConverter
    fun listToJson(type: List<Long>): String {
        return json.encodeToJsonElement(type).toString()
    }
}