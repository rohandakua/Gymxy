package com.gymxy.gymxyone.domain.helperFunctions

fun getExactTime(timeInMs: Long):Long {
    val currentTimeMillis = System.currentTimeMillis()
    val startOfDayMillis = currentTimeMillis - (currentTimeMillis % (24 * 60 * 60 * 1000))
    if(startOfDayMillis+timeInMs > currentTimeMillis){
        return startOfDayMillis + timeInMs + (24 * 60 * 60 * 1000) // for next day
    }else{
        return startOfDayMillis + timeInMs  // for this day
    }
}
