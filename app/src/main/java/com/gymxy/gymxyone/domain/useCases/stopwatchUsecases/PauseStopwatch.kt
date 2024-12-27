package com.gymxy.gymxyone.domain.useCases.stopwatchUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.StopwatchRepository
import javax.inject.Inject

class PauseStopwatch @Inject constructor(private val repository: StopwatchRepository) {
    fun execute(){
        repository.pause()
    }
}