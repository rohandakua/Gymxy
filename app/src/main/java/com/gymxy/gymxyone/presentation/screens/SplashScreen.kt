package com.gymxy.gymxyone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dotlottie.dlplayer.Mode
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.presentation.composableFunctions.GradientText
import com.gymxy.gymxyone.presentation.viewmodel.AuthViewModel
import com.gymxy.gymxyone.presentation.viewmodel.HomePageViewModel
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.gymxy.gymxyone.ui.theme.mainTextColor
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

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
    modifier: Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    homePageViewModel: HomePageViewModel = hiltViewModel()
) {
    // Modifier.safeDrawingPadding() use for the box inside the background image
    val orientation =
        LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    authViewModel.checkSignedInState()
    val isSignedIn = authViewModel.isSignedInState.collectAsState()
    val dotLottieController = DotLottieController()
    if (isSignedIn.value) {
        LaunchedEffect(true) {
            homePageViewModel.getListOfPerformedDays() // updates the list of performed days
        }
        LaunchedEffect(true) {
            homePageViewModel.getTrainingSplit() // updates the training split detail in the viewmodel

        }
        LaunchedEffect(dotLottieController.isComplete) {
            navController.navigate("home")
        }
    } else {
        LaunchedEffect(dotLottieController.isComplete) {
            navController.navigate("login")
        }

    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = mainBackgroundColor)  // Color(0xFF10110f)
    ) {
        if (orientation) {
            Image(
                painter = painterResource(id = R.drawable.mainbackgroundsvg),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.landscapemainbackground),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }


    //for all the content
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
                useFrameInterpolation = false,
                playMode = Mode.FORWARD,
                modifier = Modifier
                    .rotate(-45f)
                    .fillMaxSize(0.6f),
                controller = dotLottieController

            )

        }

    }

}