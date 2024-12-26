package com.gymxy.gymxyone.domain.models

data class Weight(
    /**
     * weight in grams , date in epoch in milliseconds
     */
    var weight : Long = 0,
    var dateInEpoch : Long = System.currentTimeMillis()
)