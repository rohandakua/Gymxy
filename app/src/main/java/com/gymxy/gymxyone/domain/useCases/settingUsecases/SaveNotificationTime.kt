package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class SaveNotificationTime @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
){
    fun execute(time:Long = System.currentTimeMillis()) : Result{
        return settingScreenDataInterface.saveNotificationTime(time)
    }
}