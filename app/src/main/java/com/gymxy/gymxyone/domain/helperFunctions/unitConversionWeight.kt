package com.gymxy.gymxyone.domain.helperFunctions

import android.annotation.SuppressLint
import com.gymxy.gymxyone.data.offline.SharedPreferenceCollectionName
import java.util.Locale

@SuppressLint("DefaultLocale")
fun unitConversionWeight(
    weightUnit: String,
    weight: Long  // weight in grams
): String {
    return if (weightUnit == SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM) {
        String.format("%4.1f kg", weight / 1000.0) // Convert to double for decimal precision
    } else {
        String.format("%4.1f lb", weight * 0.00220462) // Convert grams to pounds
    }
}

//
//fun unitConversionWeight(
//    weightUnit : String ,
//    weight : Long  // this is in grams
//) : String {
//    return if(weightUnit == SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM){
//        String.format(Locale.US,"%4.1f kg",(weight / 1000))
//    }else{
//        String.format(Locale.US,"%4.1f lb",(weight * 0.00220462))
//    }
//}