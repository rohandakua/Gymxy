package com.gymxy.gymxyone.domain.helperFunctions

import android.util.Log

fun millisecondsToTime(ms: Long): String {
    // Extract time components
    Log.d("millisToTime", "ms: $ms")
    val minutes = ((ms / 1000) / 60) % 60
    val hours = (((ms / 1000) / 60) / 60) % 24
    if (hours > 12) {
        Log.d("update time","%02d:%02d".format(hours, minutes))
        return "%02d:%02d".format(hours - 12, minutes)
    } else {
        Log.d("update time","%02d:%02d".format(hours, minutes))
        return "%02d:%02d".format(hours, minutes)
    }
}

fun millisecondsToTimeAmPm(ms: Long): String {
    // Extract time components
    Log.d("millisToTime", "ms: $ms")
    val minutes = ((ms / 1000) / 60) % 60
    val hours = (((ms / 1000) / 60) / 60) % 24
    if (hours > 12) {
        Log.d("update time","%02d:%02d".format(hours, minutes))
        return "%02d:%02d".format(hours - 12, minutes)
    } else {
        Log.d("update time","%02d:%02d".format(hours, minutes))
        return "%02d:%02d".format(hours, minutes)
    }
}