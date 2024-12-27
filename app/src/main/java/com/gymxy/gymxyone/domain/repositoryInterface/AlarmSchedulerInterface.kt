package com.gymxy.gymxyone.domain.repositoryInterface

import com.gymxy.gymxyone.domain.models.Result


interface AlarmSchedulerInterface {
    /**
     * schedule() fun is used to schedule the alarm.
     */

    suspend fun schedule(time: Long) : Result

    /**
     * cancel() fun is used to cancel the alarm.
     */
    suspend fun cancel(time: Long):Result



}