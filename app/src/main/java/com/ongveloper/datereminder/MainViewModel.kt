package com.ongveloper.datereminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val currentTimeMillis: Long
        get() = System.currentTimeMillis()
    var selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    var selectedTime = MutableStateFlow<LocalTime>(LocalTime.now())
    var selectedWhere = MutableStateFlow<String>("")
    var selectedWhat = MutableStateFlow<String>("")

    val schedule: StateFlow<Schedule?> = combine(
        selectedDate, selectedTime, selectedWhere, selectedWhat
    ) { date, time, where, what ->
        Schedule(
            date,
            time,
            where,
            what
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        null
    )

    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectedDate.value = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
    }

    fun onTimeChanged(hour: Int, minute: Int) {
        selectedTime.value = LocalTime.of(hour, minute)
    }

    fun onSaveClick() {
        schedule.value.canSave {
            Timber.e("save ${it}")
        }
    }
}