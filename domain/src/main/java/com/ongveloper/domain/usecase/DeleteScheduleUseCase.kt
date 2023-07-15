package com.ongveloper.domain.usecase

import com.ongveloper.domain.repository.ScheduleRepository
import javax.inject.Inject

class DeleteScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(id: Long): Result<Int> =
        runCatching {
            scheduleRepository.deleteSchedule(id)
        }
}