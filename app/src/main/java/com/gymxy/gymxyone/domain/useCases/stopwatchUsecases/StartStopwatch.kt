package com.gymxy.gymxyone.domain.useCases.stopwatchUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.StopwatchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartStopwatch @Inject constructor(private val stopwatchRepository: StopwatchRepository) {
    fun execute() {
        CoroutineScope(Dispatchers.IO).launch {
            stopwatchRepository.start()
        }
    }
}