package com.gymxy.gymxyone.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.navigation.compose.rememberNavController
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.presentation.navigation.NavControllerClass
import com.gymxy.gymxyone.ui.theme.CustomTheme
import com.gymxy.gymxyone.ui.theme.GymxyTheme
import com.gymxy.gymxyone.ui.theme.mainCardBackground
import com.gymxy.gymxyone.ui.theme.mainTextColor
import com.gymxy.gymxyone.ui.theme.secondaryTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val c = CredentialManager.create(this)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CustomTheme {
               // SplashScreen(modifier = Modifier,navController = rememberNavController() )
                //GoogleSignInScreen(modifier = Modifier, navController = rememberNavController() , activityContext = this, activity = this@MainActivity)
               // HomeScreen()
//                SettingPage()
                val navController = rememberNavController()
                NavControllerClass(
                    navController = navController,
                    activityContext = this,
                    activity = this@MainActivity,
                    modifier = Modifier
                )

           }
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
    Card(modifier = Modifier.fillMaxSize(.7f), colors = CardDefaults.cardColors(containerColor = mainCardBackground)) {
        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Hello This is re", color = mainTextColor,fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text(text = "12345678910", color = mainTextColor)
            Text(text = "Hello", color = mainTextColor)
            Text(text = "Hello This is re", color = secondaryTextColor,fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text(text = "12345678910", color = secondaryTextColor)
            Text(text = "Hello", color = secondaryTextColor)
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
