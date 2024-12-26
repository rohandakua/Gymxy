package com.gymxy.gymxyone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dotlottie.dlplayer.Mode
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

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
    navController: NavController
) {
    val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()
    val insets = WindowInsets.navigationBars.getBottom(LocalDensity.current)
    val bottomPadding = if (insets > 0) insets.dp else 0.dp
    var orientation = LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
            .background(mainBackgroundColor) // Apply navigation bar padding
    ) {
        if(orientation){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.mainbackgroundsvg),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        }else{
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id=R.drawable.landscapemainbackground),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Url("https://lottie.host/3b167c52-a0e1-4816-8e6c-755fd382a465/6Pdggk8Ksp.lottie"),
                autoplay = true,
                loop = true,
                speed = 1f,
                useFrameInterpolation = false,
                playMode = Mode.FORWARD,
                modifier = Modifier
                    .rotate(-45f)
                    .fillMaxSize(.6f)
            )

            //TODO  make a call to the viewModel for checking the state , if
            // User is logged in then load the main screen or else load the login screen


            //val dotLottieController = remember { DotLottieController() }

//    LaunchedEffect(UInt) {
//        dotLottieController.setLoop(true)
//        dotLottieController.setSpeed(3f)
//        // Play
//        dotLottieController.play()
//        // Pause
//        dotLottieController.pause()
//        // Stop
//        dotLottieController.play()
//    }
//    DotLottieAnimation(
//        source = DotLottieSource.Url("https://lottie.host/a5601e71-4b1b-49a3-bea7-2cdfc3d6e7f9/83sIPSf3bp.lottie"),
//        autoplay = true,
//        loop = true,
//        speed = 1f,
//        useFrameInterpolation = false,
//        playMode = Mode.FORWARD,
//        modifier = Modifier.fillMaxSize(.5f).blur(radius = 1.dp)
//    )

        }
    }
}