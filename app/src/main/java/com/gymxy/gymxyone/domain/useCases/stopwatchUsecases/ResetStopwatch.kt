package com.gymxy.gymxyone.domain.useCases.stopwatchUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.StopwatchRepository
import javax.inject.Inject

class ResetStopwatch @Inject constructor(private val stopwatchRepository: StopwatchRepository) {
    fun execute() {
        stopwatchRepository.reset()
    }
}