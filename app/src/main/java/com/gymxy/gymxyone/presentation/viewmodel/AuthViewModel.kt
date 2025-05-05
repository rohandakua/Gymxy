package com.gymxy.gymxyone.presentation.viewmodel


import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.GetUid
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.IsSignedIn
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.Login
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.Logout
import com.gymxy.gymxyone.presentation.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel for authentication-related operations.
 * this is used in @SplashScreen.kt for checking the signup related task
 *
 * Objective of this viewmodel :-
 *      Check if the user is logged in , if not then move to the GoogleSignInScreen.kt
 *      if logged in then move to the HomeScreen.kt
 *          also make sure to load the data of HomeScreen.kt in the HomePageViewModel (inject it here)
 *      ALSO this viewmodel will handle the signing out of the user
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val login: Login,
    private val logout: Logout,
    private val isSignedIn: IsSignedIn,
    private val getUid: GetUid
) : ViewModel() {

    //STATEs

    /**
     * true is signed In , false is not signed in
     */
    private val _isSignedInState = MutableStateFlow(false)
    val isSignedInState: StateFlow<Boolean> get() = _isSignedInState
    private fun setIsSignedIn(value: Boolean){      // only changed by the fun checkSignedInState
        _isSignedInState.value = value
    }

    private  val _loadingState = MutableStateFlow(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> get()= _loadingState
    fun setLoadingState(loadingState : LoadingState){
        _loadingState.value = loadingState
    }


    /**
     * call when you need to update the signed in state
     */
    fun checkSignedInState(){
        setIsSignedIn(isSignedIn.execute())
    }



    suspend fun login(activityContext: Context,activity: Activity): Result {
        val result = login.execute(activityContext,activity)
        if(result==Result.SUCCESS){
            setIsSignedIn(true)
            setLoadingState(LoadingState.Loaded)
        }
        return result
    }

    suspend fun logout(): Result {
        val result= logout.execute()
        if(result==Result.SUCCESS){
            setIsSignedIn(false)
            setLoadingState(LoadingState.Idle)
        }
        return result
    }



}
