package com.gymxy.gymxyone.data.offline

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceDataHandler @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveUid(uid: String) {
        sharedPreferences.edit()
            .putString(SharedPreferenceCollectionName.SHARED_PREFERENCE_UID, uid).apply()
    }

    fun getUid(): String? {
        return sharedPreferences.getString(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_UID,
            null
        )
    }

    fun saveName(name: String) {
        sharedPreferences.edit()
            .putString(SharedPreferenceCollectionName.SHARED_PREFERENCE_NAME, name).apply()
    }

    fun getName(): String? {
        return sharedPreferences.getString(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_NAME,
            null
        )
    }

    fun savePhoto(photo: String) {
        sharedPreferences.edit()
            .putString(SharedPreferenceCollectionName.SHARED_PREFERENCE_PHOTO_URL, photo).apply()
    }

    fun getPhoto(): String? {
        return sharedPreferences.getString(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_PHOTO_URL,
            null
        )
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveWeightUnit(unit: String) {
        if (unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM || unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_POUND) {
            sharedPreferences.edit()
                .putString(SharedPreferenceCollectionName.SHARED_PREFERENCE_WEIGHT_UNIT, unit)
                .apply()
        }
    }

    fun getWeightUnit(): String? {
        return sharedPreferences.getString(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_WEIGHT_UNIT,
            SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM
        )
    }

    fun saveHeightUnit(unit: String) {
        if (unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_CENTIMETER || unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_FEET_AND_INCHES) {
            sharedPreferences.edit().putString(
                SharedPreferenceCollectionName.SHARED_PREFERENCE_HEIGHT_UNIT,
                unit
            ).apply()
        }
    }

    fun getHeightUnit(): String? {
        return sharedPreferences.getString(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_HEIGHT_UNIT,
            SharedPreferenceCollectionName.SHARED_PREFERENCE_CENTIMETER
        )
    }

    fun saveNotificationPermission(permission: Boolean) {
        sharedPreferences.edit().putBoolean(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_GET_NOTIFICATIONS_OR_NOT,
            permission
        ).apply()
    }

    fun getNotificationPermission(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_GET_NOTIFICATIONS_OR_NOT,
            false
        )
    }

    fun saveNotificationTime(time: Long) {
        sharedPreferences.edit().putLong(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_TIME_TO_GET_NOTIFICATIONS,
            time
        ).apply()
    }

    fun getNotificationTime(): Long {
        return sharedPreferences.getLong(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_TIME_TO_GET_NOTIFICATIONS,
            0
        )
    }

    fun saveRatingOrNot(permission: Boolean) {
        sharedPreferences.edit()
            .putBoolean(SharedPreferenceCollectionName.SHARED_PREFERENCE_HAVE_RATING_OR_NOT, permission)
            .apply()
    }
    fun getRatingOrNot(): Boolean {
        return sharedPreferences.getBoolean(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_HAVE_RATING_OR_NOT,
            true
        )
    }
    fun saveSplitId(splitId: String){
        sharedPreferences.edit().putString(SharedPreferenceCollectionName.SHARED_PREFERENCE_SPLIT_ID,splitId).apply()
    }
    fun getSplitId(): String? {
        return sharedPreferences.getString(
            SharedPreferenceCollectionName.SHARED_PREFERENCE_SPLIT_ID,
            null
        )
    }
    fun saveBMI(bmi : String){
        sharedPreferences.edit().putString(SharedPreferenceCollectionName.SHARED_PREFERENCE_BMI,bmi).apply()
    }
    fun getBMI(): String?{
        return sharedPreferences.getString(SharedPreferenceCollectionName.SHARED_PREFERENCE_BMI,null)
    }

}