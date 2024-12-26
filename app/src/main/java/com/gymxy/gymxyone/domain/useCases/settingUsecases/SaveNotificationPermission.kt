package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class SaveNotificationPermission @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
){
    fun execute(permission: Boolean = true) : Result {
        return settingScreenDataInterface.saveNotificationPermission(permission)
    }
}