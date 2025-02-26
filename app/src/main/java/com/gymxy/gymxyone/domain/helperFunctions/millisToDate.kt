package com.gymxy.gymxyone.domain.helperFunctions
import java.text.SimpleDateFormat
import java.util.*

fun millisToDate(milliseconds: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
    return sdf.format(Date(milliseconds))
}