package com.gymxy.gymxyone.domain.helperFunctions

fun kilogramToGram (weightInDouble: Double):Long {
    val weightInGram = weightInDouble * 1000
    return weightInGram.toLong()
}