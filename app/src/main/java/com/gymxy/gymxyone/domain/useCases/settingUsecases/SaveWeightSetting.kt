package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SaveWeightSetting @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    suspend fun execute(
        weight: Double = 0.0
    ):Result{
        return withContext(Dispatchers.IO){ settingScreenDataInterface.saveWeight(weight)}
    }
}