package com.gymxy.gymxyone.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.ui.theme.GymxyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val c = CredentialManager.create(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting(name = "hello android")
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var orientation = LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFF10110f))) {
        if(orientation){
            Image(
                painter = painterResource(id = R.drawable.mainbackgroundsvg),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }else {
            Image(
                painter = painterResource(id = R.drawable.landscapemainbackground),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymxyTheme {
        Greeting("Android")
    }
}