package com.gymxy.gymxyone.domain.useCases.googleAuthUseCase

import com.gymxy.gymxyone.auth.GoogleAuthClient
import com.gymxy.gymxyone.domain.models.Result
import javax.inject.Inject


class GetUid @Inject constructor(
    private val googleAuthClient: GoogleAuthClient
){
    fun execute (
    ): String? {
        return googleAuthClient.getUid()
    }
}