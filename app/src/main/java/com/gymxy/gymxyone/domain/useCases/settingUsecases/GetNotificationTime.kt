package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class GetNotificationTime @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    fun execute(
    ): Long {
        return settingScreenDataInterface.getNotificationTime()
    }
}