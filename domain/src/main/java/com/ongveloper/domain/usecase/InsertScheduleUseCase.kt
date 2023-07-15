package com.ongveloper.domain.usecase

import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.repository.ScheduleRepository
import javax.inject.Inject

class InsertScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(schedule: Schedule): Result<Long> =
        runCatching {
            scheduleRepository.insertSchedule(schedule)
        }
}