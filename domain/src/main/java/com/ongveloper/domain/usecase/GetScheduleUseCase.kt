package com.ongveloper.domain.usecase

import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
        private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(id: Long): Schedule =
            scheduleRepository.getSchedule(id)
}