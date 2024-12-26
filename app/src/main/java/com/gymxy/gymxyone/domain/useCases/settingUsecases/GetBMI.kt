package com.gymxy.gymxyone.domain.useCases.settingUsecases

import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBMI @Inject constructor(
    private val settingScreenDataInterface: SettingScreenDataInterface
) {
    suspend fun execute(
    ):String {
        return withContext(Dispatchers.IO){ settingScreenDataInterface.getBMI()}
    }
}