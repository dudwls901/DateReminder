package com.ongveloper.datereminder

sealed class MainState{
    data class SaveSchedule(val savedSchedule: SavedSchedule): MainState()
}
