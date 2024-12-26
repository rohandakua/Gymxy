package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject


class SaveHeightSetting @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    suspend fun execute(
        height: Double = 0.0,
        feet: Double = 0.0,
        inch: Double = 0.0
    ):Result{
        return settingScreenDataInterface.saveHeight(height, feet, inch)
    }
}