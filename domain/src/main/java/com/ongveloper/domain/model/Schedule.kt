package com.ongveloper.domain.model

import java.time.LocalDateTime

data class Schedule(
    val scheduleParams: ScheduleParams,
    val diffMinutes: Long,
    val idx: Int,
    val alarmTimes: List<LocalDateTime>
)