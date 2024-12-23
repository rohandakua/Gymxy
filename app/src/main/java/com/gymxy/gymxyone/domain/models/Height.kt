package com.gymxy.gymxyone.domain.models
/**
 * height in centimeters , date in epoch in milliseconds
 */
data class Height(
    var height : Int = 0,
    var dateInEpoch : Long = System.currentTimeMillis()
)