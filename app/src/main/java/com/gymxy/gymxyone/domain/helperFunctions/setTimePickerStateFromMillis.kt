package com.gymxy.gymxyone.domain.helperFunctions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

@OptIn(ExperimentalMaterial3Api::class)
fun setTimePickerStateFromMillis(timePickerState: TimePickerState, millisFromMidnight: Long) {
    val totalMinutes = millisFromMidnight / (60 * 1000)
    val hours = (totalMinutes / 60).toInt()
    val minutes = (totalMinutes % 60).toInt()
    timePickerState.hour = hours
    timePickerState.minute = minutes
}
