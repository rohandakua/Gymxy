package com.gymxy.gymxyone.domain.helperFunctions

fun mapToList(details: Map<Int, String>): List<String> {
    val result = MutableList(details.keys.maxOrNull()?.plus(1) ?: 0) { "" }
    details.forEach { (key, value) ->
        if (key in result.indices) {
            result[key] = value
        }
    }
    return result
}
