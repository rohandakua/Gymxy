package com.gymxy.gymxyone.domain.helperFunctions

fun millisecondsToTime(ms: Long): String {
    // Extract time components
    val minutes = ((ms / 1000) / 60) % 60
    val hours = (((ms / 1000) / 60) / 60) % 24
    if (hours > 12) {
        return "%02d : %02d pm".format(hours - 12, minutes)
    } else {
        return "%02d : %02d am".format(hours, minutes)
    }
}