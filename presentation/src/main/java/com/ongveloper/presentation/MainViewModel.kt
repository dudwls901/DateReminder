package com.ongveloper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.model.ScheduleParams
import com.ongveloper.domain.model.canSave
import com.ongveloper.domain.model.getDateTime
import com.ongveloper.domain.usecase.DeleteScheduleUseCase
import com.ongveloper.domain.usecase.GetSchedulesFlowUseCase
import com.ongveloper.domain.usecase.InsertScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSchedules: GetSchedulesFlowUseCase,
    private val insertScheduleUseCase: InsertScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
) : ViewModel() {
    val currentTimeMillis: Long
        get() = System.currentTimeMillis()
    val currentDateTime: LocalDateTime
        get() = LocalDateTime.now()

    var selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    var selectedTime = MutableStateFlow<LocalTime>(LocalTime.now())
    var selectedWhere = MutableStateFlow<String>("")
    var selectedWhat = MutableStateFlow<String>("")

    val scheduleParams: StateFlow<ScheduleParams?> = combine(
        selectedDate, selectedTime, selectedWhere, selectedWhat
    ) { date, time, where, what ->
        ScheduleParams(
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

    val schedules = getSchedules().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        null
    )

    private val _mainState: MutableStateFlow<MainState> = MutableStateFlow(MainState.Init)
    val mainState: StateFlow<MainState>
        get() = _mainState.asStateFlow()

    val mainEvent = MutableSharedFlow<MainEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectedDate.value = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
    }

    fun onTimeChanged(hour: Int, minute: Int) {
        selectedTime.value = LocalTime.of(hour, minute)
    }

    fun onSaveClick() {
        scheduleParams.value.canSave {
            Timber.e("save ${it}")
            val diff = Duration.between(currentDateTime, it.getDateTime())
            val diffMinute = diff.toMinutes()
            val diffDay = diff.toDays()
            val hour = diff.toHours()
            Timber.e("diffDay: ${diffDay} \n diffHour : $hour\n diffMinute ${diffMinute}")
            val schedule = Schedule(
                0,
                it,
                diffMinute,
                diffMinute.toAlarmTimes(
                    currentDateTime,
                    it.getDateTime()
                ),
                false
            )
            Timber.e("schedule: ${schedule}")
            viewModelScope.launch {
                insertScheduleUseCase(schedule).onSuccess {
                    mainEvent.emit(
                        MainEvent.SaveSchedule(schedule)
                    )
                }.onFailure {
                    Timber.e("fail save schedule")
                }
            }
        }
    }
}