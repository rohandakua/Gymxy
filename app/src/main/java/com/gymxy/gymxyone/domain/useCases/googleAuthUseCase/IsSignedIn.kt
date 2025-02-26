package com.gymxy.gymxyone.domain.useCases.googleAuthUseCase

import com.gymxy.gymxyone.auth.GoogleAuthClient
import javax.inject.Inject


class IsSignedIn @Inject constructor(
    private val googleAuthClient: GoogleAuthClient
){
    fun execute (
    ): Boolean {
        return googleAuthClient.isSignedIn()
    }
}