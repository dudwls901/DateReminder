package com.ongveloper.domain.usecase

import com.ongveloper.domain.model.Schedule
import com.ongveloper.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSchedulesFlowUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
) {
    operator fun invoke(): Flow<List<Schedule>> =
        scheduleRepository.getSchedulesFlow()
}