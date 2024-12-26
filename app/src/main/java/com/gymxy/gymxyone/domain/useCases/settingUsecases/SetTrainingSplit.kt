package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import javax.inject.Inject

class SetTrainingSplit @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    fun execute(
        splitId : String
    ):Result {
        return settingScreenDataInterface.setTrainingSplit(splitId)
    }
}