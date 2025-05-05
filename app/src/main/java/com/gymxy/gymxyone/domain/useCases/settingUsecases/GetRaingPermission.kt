package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class GetRaingPermission@Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    fun execute(
    ): Boolean {
        return settingScreenDataInterface.getRatingOrNot()
    }
}