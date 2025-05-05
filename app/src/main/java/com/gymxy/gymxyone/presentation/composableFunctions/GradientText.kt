package com.gymxy.gymxyone.presentation.composableFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.ui.theme.LexendDeca
import com.gymxy.gymxyone.ui.theme.gradient1
import com.gymxy.gymxyone.ui.theme.gradient2

@Composable
fun GradientText(
    text: String,
    modifier: Modifier = Modifier,
    textSize : Int = 48
) {


    BasicText(
        text = text,
        style = TextStyle(
            brush = gradientBrush,
            fontSize = textSize.sp,
            fontFamily = LexendDeca,
            fontWeight = FontWeight.Bold // You can adjust this
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun P() {
    Column {
        GradientText(text = "Hello This is re")
        GradientText(text = "12345678910")
        GradientText(text = "Hello")
    }
}