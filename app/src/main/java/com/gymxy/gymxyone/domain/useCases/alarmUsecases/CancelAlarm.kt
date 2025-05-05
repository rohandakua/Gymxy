package com.gymxy.gymxyone.domain.useCases.alarmUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.AlarmSchedulerInterface
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CancelAlarm @Inject constructor(
    private val alarmSchedulerInterface: AlarmSchedulerInterface,
    private val getNotificationTime: GetNotificationTime
) {
    suspend fun execute() {
        withContext(Dispatchers.Default){
            val time = getNotificationTime.execute()
            alarmSchedulerInterface.cancel(time)
        }
    }
}