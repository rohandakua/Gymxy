package com.gymxy.gymxyone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dotlottie.dlplayer.Mode
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.presentation.viewmodel.AuthViewModel
import com.gymxy.gymxyone.presentation.viewmodel.HomePageViewModel
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

//
//@Preview
//@Composable
//fun splashScreenPreview() {
//    GymxyTheme {
//        SplashScreen(modifier = Modifier.fillMaxSize())
//    }
//
//}
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    homePageViewModel: HomePageViewModel = hiltViewModel()
) {
    val orientation = LocalConfiguration.current.orientation
    val isSignedIn = authViewModel.isSignedInState.collectAsState()
    val dotLottieController = remember { DotLottieController() }
    val animationComplete = remember { mutableStateOf(false) }

    LaunchedEffect(dotLottieController) {
        while (!dotLottieController.isComplete) {
            delay(400)
        }
        animationComplete.value = true
    }

    LaunchedEffect(animationComplete.value) {
        if (animationComplete.value) {
            authViewModel.checkSignedInState()
            if (isSignedIn.value) {
                try {
                    coroutineScope {
                        val performedDaysDeferred = async { homePageViewModel.getListOfPerformedDays() }
                        val trainingSplitDeferred = async { homePageViewModel.getTrainingSplit() }
                        awaitAll(performedDaysDeferred, trainingSplitDeferred)
                    }
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                } catch (e: Exception) {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = mainBackgroundColor)
    ) {
        Image(
            painter = painterResource(
                id = when (orientation) {
                    android.content.res.Configuration.ORIENTATION_PORTRAIT -> R.drawable.mainbackgroundsvg
                    else -> R.drawable.landscapemainbackground
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

    Box(modifier = Modifier.safeDrawingPadding()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Url("https://lottie.host/3b167c52-a0e1-4816-8e6c-755fd382a465/6Pdggk8Ksp.lottie"),
                autoplay = true,
                loop = false,
                speed = 1f,
                useFrameInterpolation = true,
                playMode = Mode.FORWARD,
                modifier = Modifier
                    .rotate(-45f)
                    .fillMaxSize(0.6f),
                controller = dotLottieController
            )
        }
    }
}