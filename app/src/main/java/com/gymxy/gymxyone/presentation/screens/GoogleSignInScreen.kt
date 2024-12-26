package com.gymxy.gymxyone.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
private fun prevGoogleSignInScreen(
) {
    GoogleSignInScreen(modifier = Modifier, navController = rememberNavController())

}

@Composable
fun GoogleSignInScreen (
    modifier: Modifier,
    navController: NavController,
){
}