package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class SaveWeightUnit @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    fun execute(unit: String
    ): Result {
        return settingScreenDataInterface.saveWeightUnit(unit)
    }
}