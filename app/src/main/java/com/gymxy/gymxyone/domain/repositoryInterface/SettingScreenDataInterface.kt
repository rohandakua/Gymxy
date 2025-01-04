package com.gymxy.gymxyone.domain.repositoryInterface

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.SplitDetails

interface SettingScreenDataInterface {
    fun saveWeightUnit(unit: String):Result
    fun getWeightUnit():String
    suspend fun saveWeight (weight: Double):Result
    fun saveHeightUnit(unit: String):Result
    fun getHeightUnit():String
    suspend fun saveHeight (height: Double , feet : Double , inch : Double):Result
    fun saveNotificationPermission(permission: Boolean):Result
    fun getNotificationPermission():Boolean
    fun saveNotificationTime(time: Long):Result
    fun getNotificationTime():Long
    fun saveRatingOrNot(permission: Boolean):Result
    fun getRatingOrNot():Boolean
    suspend fun getBMI():String
    suspend fun setBMI()
    fun setTrainingSplit(splitId : String):Result
    suspend fun getTrainingSplit():SplitDetails?
    fun getHeight():Int
    fun getWeight():Long
    fun getPhotoAndUrl(): Pair<String,String>


}