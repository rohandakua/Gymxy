package com.gymxy.gymxyone.domain.helperFunctions

/**
 *  1 inch = 2.54 cm
 *  1 foot = 30.48 cm
 */
fun feetInchesToCms(feet: Double, inch: Double): Int {
    val feetToCm = feet * 30.48
    val inchToCm = inch * 2.54
    val totalCm = feetToCm + inchToCm
    return totalCm.toInt()
}
