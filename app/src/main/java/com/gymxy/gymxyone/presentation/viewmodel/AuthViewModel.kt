package com.gymxy.gymxyone.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.Login
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.Logout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val login: Login,
    private val logout: Logout
) : ViewModel() {

    suspend fun login(): Result {
        return login.execute(CoroutineScope(Dispatchers.IO))
    }

    suspend fun logout(): Result {
        return logout.execute()
    }



}
