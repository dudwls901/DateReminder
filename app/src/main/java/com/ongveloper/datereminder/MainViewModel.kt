package com.ongveloper.datereminder

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    var selectedTime = MutableStateFlow<LocalTime>(LocalTime.now())
    var selectedWhere = MutableStateFlow<String>("")
    var selectedWhat = MutableStateFlow<String>("")

    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectedDate.value = LocalDate.of(year, monthOfYear + 1,dayOfMonth)
    }
    fun onTimeChanged(hour: Int, minute: Int){
        selectedTime.value = LocalTime.of(hour, minute)
    }
}