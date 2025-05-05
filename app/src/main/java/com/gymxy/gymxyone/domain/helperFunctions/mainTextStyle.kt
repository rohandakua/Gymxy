package com.gymxy.gymxyone.domain.helperFunctions

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gymxy.gymxyone.ui.theme.LexendDeca


val mainTextStyle = TextStyle(
    fontSize = 20.sp,
    fontFamily = LexendDeca,
    fontWeight = FontWeight.Bold
)
fun provideMainTextStyle(fontSize: Int) = mainTextStyle.copy(fontSize = fontSize.sp)