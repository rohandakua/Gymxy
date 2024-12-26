package com.gymxy.gymxyone.domain.helperFunctions

fun calculateBMI(weight: Long, height: Int): String {
    val heightInMeters = height / 100.0
    return (weight.toDouble() / (heightInMeters * heightInMeters)).toString()
}