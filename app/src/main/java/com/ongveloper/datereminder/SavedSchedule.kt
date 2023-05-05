package com.ongveloper.datereminder

import java.time.LocalDateTime

data class SavedSchedule(
    val schedule: Schedule,
    val diffMinutes: Long,
    val idx: Int,
    val alarmTimes: List<LocalDateTime>
)