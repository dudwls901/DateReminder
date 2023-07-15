package com.ongveloper.presentation

sealed class MainState {
    object Init: MainState()
    data class FailedSaveSchedule(private val message: String): MainState()
}