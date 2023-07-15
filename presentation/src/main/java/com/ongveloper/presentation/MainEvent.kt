package com.ongveloper.presentation

import com.ongveloper.domain.model.Schedule

sealed class MainEvent{
    data class SaveSchedule(val schedule: Schedule): MainEvent()
}
