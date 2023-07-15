package com.ongveloper.datereminder

import com.ongveloper.domain.model.Schedule

sealed class MainEvent{
    data class SaveSchedule(val schedule: Schedule): MainEvent()
}
