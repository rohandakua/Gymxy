package com.gymxy.gymxyone.domain.useCases.googleAuthUseCase

import android.app.Activity
import android.content.Context
import com.gymxy.gymxyone.auth.GoogleAuthClient
import javax.inject.Inject
import com.gymxy.gymxyone.domain.models.Result
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext


class Login @Inject constructor(
    private val googleAuthClient: GoogleAuthClient
){
    suspend fun execute (
        activityContext: Context
        ,activity: Activity
    ) : Result {
        return googleAuthClient.signIn(activityContext,activity)
    }
}