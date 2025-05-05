package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class GetHeight @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    fun execute(
    ): Int {
        return settingScreenDataInterface.getHeight()
    }
}