package com.gymxy.gymxyone.presentation.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gymxy.gymxyone.presentation.screens.GoogleSignInScreen
import com.gymxy.gymxyone.presentation.screens.HomeScreen
import com.gymxy.gymxyone.presentation.screens.SaveExercisePage
import com.gymxy.gymxyone.presentation.screens.SplashScreen

/**
 * HomeScreen = home
 * GoogleSignInScreen = login
 * SettingScreen = settings
 * SplashScreen = splash
 * SaveExercisePage = saveExercise
 */

@Composable
fun NavControllerClass(
    navController: NavHostController ,
    activityContext: Context,
    activity: Activity,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = "splash"){
        composable("login"){
            GoogleSignInScreen(modifier = modifier, navController = navController, activityContext = activityContext , activity = activity )

        }
        composable("home"){
            HomeScreen(modifier = modifier, navController = navController)

        }
        composable("setting"){
            HomeScreen(modifier = modifier, navController = navController)

        }
        composable("splash"){
            SplashScreen(modifier = modifier, navController = navController )
        }
        composable("saveExercise"){
            SaveExercisePage(modifier = modifier, navController = navController)
        }
    }
}