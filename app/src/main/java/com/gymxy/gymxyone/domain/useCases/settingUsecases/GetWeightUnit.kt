package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class GetWeightUnit @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    fun execute(
    ):String {
        return settingScreenDataInterface.getWeightUnit()
    }
}