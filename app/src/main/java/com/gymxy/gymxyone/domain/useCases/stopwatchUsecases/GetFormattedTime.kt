package com.gymxy.gymxyone.domain.useCases.stopwatchUsecases

import com.gymxy.gymxyone.domain.helperFunctions.millisecondsToTime
import com.gymxy.gymxyone.domain.repositoryInterface.StopwatchRepository
import javax.inject.Inject

class GetFormattedTime @Inject constructor(
    private val stopwatchRepository: StopwatchRepository
) {
    fun execute():String{
        return millisecondsToTime(stopwatchRepository.getTimeInMillis())
    }
}