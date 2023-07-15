package com.ongveloper.presentation

import java.time.LocalDateTime


fun Long.toAlarmTimes(
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime
): List<LocalDateTime>{
    var tempDateTime = startDateTime
    val term = this/ ALARM_COUNT
    return List(ALARM_COUNT){
        tempDateTime = tempDateTime.plusMinutes(term).coerceAtMost(endDateTime)
        tempDateTime
    }
}