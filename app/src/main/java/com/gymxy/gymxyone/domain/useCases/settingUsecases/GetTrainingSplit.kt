package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTrainingSplit @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    suspend fun execute(unit: String
    ): SplitDetails? {
        return withContext(Dispatchers.IO){ settingScreenDataInterface.getTrainingSplit()}
    }
}