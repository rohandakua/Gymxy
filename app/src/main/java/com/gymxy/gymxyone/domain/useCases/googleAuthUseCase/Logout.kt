package com.gymxy.gymxyone.domain.useCases.googleAuthUseCase

import com.gymxy.gymxyone.auth.GoogleAuthClient
import com.gymxy.gymxyone.domain.models.Result
import javax.inject.Inject


class Logout @Inject constructor(
    private val googleAuthClient: GoogleAuthClient
){
    suspend fun execute (
    ) : Result {
        googleAuthClient.signOut()
        return Result.SUCCESS
    }
}