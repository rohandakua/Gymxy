package com.gymxy.gymxyone.domain.helperFunctions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

@OptIn(ExperimentalMaterial3Api::class)
fun timePickerStateToMillisFromMidnight(timePickerState: TimePickerState): Long {
    val hoursInMillis = timePickerState.hour * 60 * 60 * 1000L
    val minutesInMillis = timePickerState.minute * 60 * 1000L
    return hoursInMillis + minutesInMillis
}
