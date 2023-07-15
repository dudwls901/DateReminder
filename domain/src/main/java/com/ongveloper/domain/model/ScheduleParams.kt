package com.ongveloper.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleParams(
    val date: LocalDate,
    val time: LocalTime,
    val where: String,
    val what: String,
) {
    private val nowDate = LocalDate.now()
    private val nowTime = LocalTime.now()

    val isRightDateTime: Boolean
        get() = (date > nowDate)  ||
            ( date == nowDate && time >= nowTime)

    val isRightContents: Boolean
        get() = where.isNotBlank() && what.isNotBlank()
}

fun ScheduleParams?.canSave(block: (ScheduleParams) -> Unit){
    if(this == null || isRightDateTime.not() || isRightContents.not()) return
    block(this)
}

fun ScheduleParams.getDateTime(): LocalDateTime = LocalDateTime.of(date,time)