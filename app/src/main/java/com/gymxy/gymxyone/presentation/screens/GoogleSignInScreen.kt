package com.gymxy.gymxyone.presentation.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.presentation.composableFunctions.GradientText
import com.gymxy.gymxyone.presentation.viewmodel.AuthViewModel
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch

//@Preview
//@Composable
//private fun prevGoogleSignInScreen(
//) {
//    GoogleSignInScreen(modifier = Modifier, navController = rememberNavController())
//
//}

@Composable
fun GoogleSignInScreen (
    modifier: Modifier,
    navController: NavController,
    activityContext: Context,
    authViewModel: AuthViewModel= hiltViewModel(),
    activity: Activity

){
    val orientation =
        LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val isSignedIn = authViewModel.isSignedInState.collectAsState()
    Box(
        modifier = Modifier
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
            modifier=Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                authViewModel.viewModelScope.launch {
                    authViewModel.login(activityContext,activity)
                }
            },
                colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                GradientText(text = "Google Login", textSize = 24)
            }
        }

    }




}