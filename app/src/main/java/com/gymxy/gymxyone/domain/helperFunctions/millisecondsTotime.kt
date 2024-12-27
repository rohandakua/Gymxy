package com.gymxy.gymxyone.domain.helperFunctions

fun millisecondsToTime(ms: Long): String {
    // Extract time components
    val minutes = ((ms / 1000) / 60) % 60
    val hours = (((ms / 1000) / 60) / 60) % 24

    // Format each component to be exactly two characters long
    return "%02d : %02d".format(hours, minutes)
}