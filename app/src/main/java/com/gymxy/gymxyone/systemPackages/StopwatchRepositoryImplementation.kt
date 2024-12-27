package com.gymxy.gymxyone.systemPackages

import com.gymxy.gymxyone.domain.repositoryInterface.StopwatchRepository
import kotlinx.coroutines.delay


class StopwatchRepositoryImplementation : StopwatchRepository {
    private var timeInMillis : Long = 0L
    private var isRunning : Boolean = false
    private var timeStart : Long = 0L

    override suspend fun start() {
        isRunning = true
        timeStart = System.currentTimeMillis() - timeInMillis // Resume from previous time
        while (isRunning) {
            timeInMillis = System.currentTimeMillis() - timeStart
            delay(1000)
        }

    }

    override fun reset() {
        isRunning=false
        timeInMillis=0L
        timeStart=0L
    }

    override fun pause() {
        isRunning=false
    }

    override fun getTimeInMillis(): Long {
        return timeInMillis
    }

    override fun isRunning(): Boolean {
        return isRunning
    }


}