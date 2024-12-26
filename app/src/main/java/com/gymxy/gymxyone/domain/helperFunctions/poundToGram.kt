package com.gymxy.gymxyone.domain.helperFunctions

    /**
    * Conversion factor: 1 pound = 453.592 grams
    */
fun poundToGram(weightInDouble: Double): Long {
    val grams = weightInDouble * 453.592
    return grams.toLong()
}
