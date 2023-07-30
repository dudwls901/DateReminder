package com.ongveloper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.usecase.GetScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
        private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {

    private val _schedule: MutableStateFlow<Schedule?> = MutableStateFlow(null)
    val schedule: StateFlow<Schedule?> = _schedule.asStateFlow()

    fun setSchedule(id: Long) {
        viewModelScope.launch {
            _schedule.emit(getScheduleUseCase(id))
        }
    }
}