package com.gymxy.gymxyone.domain.helperFunctions

/**
 *  1 inch = 2.54 cm
 *  1 foot = 30.48 cm
 */
fun cmsToFeetInches(cms : Int): Pair<Double, Double> {
    return Pair(first = cms/(30.48), second = cms/(2.54))
}