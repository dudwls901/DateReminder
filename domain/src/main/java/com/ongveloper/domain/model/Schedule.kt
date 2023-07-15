package com.ongveloper.domain.model

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class Schedule(
    val id: Long,
    val scheduleParams: ScheduleParams,
    val diffMinutes: Long,
    val alarmTimes: List<LocalDateTime>,
    val isFinished: Boolean
) {
    companion object {
        fun List<Long>.mapLocalDateTime(): List<LocalDateTime> =
            map { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }

        fun List<LocalDateTime>.mapTimeMillis(): List<Long> =
            map { it.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() }
    }
}