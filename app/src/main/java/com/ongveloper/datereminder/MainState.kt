package com.ongveloper.datereminder

import com.ongveloper.domain.model.Schedule

sealed class MainState{
    data class SaveSchedule(val schedule: Schedule): MainState()
}
