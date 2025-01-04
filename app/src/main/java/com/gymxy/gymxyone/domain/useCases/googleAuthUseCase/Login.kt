package com.gymxy.gymxyone.domain.useCases.googleAuthUseCase

import com.gymxy.gymxyone.auth.GoogleAuthClient
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import com.gymxy.gymxyone.domain.models.Result


class Login @Inject constructor(
    private val googleAuthClient: GoogleAuthClient
){
    suspend fun execute (
        coroutineScope: CoroutineScope
    ) : Result {
        return googleAuthClient.signIn(coroutineScope)
    }
}