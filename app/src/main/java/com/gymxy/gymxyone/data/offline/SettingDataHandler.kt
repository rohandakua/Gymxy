package com.gymxy.gymxyone.data.offline

import com.gymxy.gymxyone.domain.helperFunctions.calculateBMI
import com.gymxy.gymxyone.domain.helperFunctions.feetInchesToCms
import com.gymxy.gymxyone.domain.helperFunctions.kilogramToGram
import com.gymxy.gymxyone.domain.helperFunctions.poundToGram
import com.gymxy.gymxyone.domain.models.Height
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.models.Weight
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetHeightDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetSplitById
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetWeightDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveHeight
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveWeight
import javax.inject.Inject

class SettingDataHandler @Inject constructor(
    private val sharedPreferenceDataHandler: SharedPreferenceDataHandler,
    private val saveHeight: SaveHeight,
    private val saveWeight: SaveWeight,
    private val getSplitById: GetSplitById,
    private val getWeightDetails: GetWeightDetails,
    private val getHeightDetails: GetHeightDetails
) : SettingScreenDataInterface {
    override fun saveWeightUnit(unit: String): Result {
        if (unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM || unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_POUND) {
            sharedPreferenceDataHandler.saveWeightUnit(unit)
            return Result.SUCCESS
        }
        return Result.FAILURE
    }

    override fun getWeightUnit(): String {
        return sharedPreferenceDataHandler.getWeightUnit().toString()
    }

    /**
     * saveWeight also calculates the bmi and saves it in shared preference
     */
    override suspend fun saveWeight(weight: Double): Result {
        val unit = getWeightUnit()
        return if(unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM){
            val result = saveWeight.execute(
                weightDetails = Weight(
                    weight = kilogramToGram(weight),
                    dateInEpoch = System.currentTimeMillis()
                )
            )
            setBMI()
            result
        }else{
            val result = saveWeight.execute(
                weightDetails = Weight(
                    weight = poundToGram(weight),
                    dateInEpoch = System.currentTimeMillis()
                )
            )
            setBMI()
            result
        }
    }

    override fun saveHeightUnit(unit: String): Result {
        if (unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_CENTIMETER || unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_FEET_AND_INCHES) {
            sharedPreferenceDataHandler.saveHeightUnit(unit)
            return Result.SUCCESS
        }
        return Result.FAILURE
    }

    override fun getHeightUnit(): String {
        return sharedPreferenceDataHandler.getHeightUnit().toString()
    }

    /**
     * saveHeight also calculates the BMI and saves it in sharedPreference
     */
    override suspend fun saveHeight(height: Double , feet : Double , inch : Double): Result {
        val unit = getHeightUnit()
        return if(unit == SharedPreferenceCollectionName.SHARED_PREFERENCE_CENTIMETER){
            val result = saveHeight.execute(
                Height(
                    height = height.toInt(),
                    dateInEpoch = System.currentTimeMillis()
                )
            )
            setBMI()
            result
        }else{
            val result = saveHeight.execute(
                Height(
                    height = feetInchesToCms(feet,inch),
                    dateInEpoch = System.currentTimeMillis()
                )
            )
            setBMI()
            result
        }
    }

    override fun saveNotificationPermission(permission: Boolean): Result {
        sharedPreferenceDataHandler.saveNotificationPermission(permission)
        return Result.SUCCESS
    }

    override fun getNotificationPermission(): Boolean {
        return sharedPreferenceDataHandler.getNotificationPermission()
    }

    override fun saveNotificationTime(time: Long): Result {
        sharedPreferenceDataHandler.saveNotificationTime(time)
        return Result.SUCCESS
    }

    override fun getNotificationTime(): Long {
        return sharedPreferenceDataHandler.getNotificationTime()
    }

    override fun saveRatingOrNot(permission: Boolean): Result {
        sharedPreferenceDataHandler.saveRatingOrNot(permission)
        return Result.SUCCESS
    }

    override fun getRatingOrNot(): Boolean {
        return sharedPreferenceDataHandler.getRatingOrNot()
    }

    override suspend fun setBMI(){
        val heightList = getHeightDetails.execute()
        val weightList = getWeightDetails.execute()
        val height = heightList?.first()?.height
        val weight = weightList?.first()?.weight
        val bmi = calculateBMI(weight!!,height!!)
        sharedPreferenceDataHandler.saveBMI(bmi)
    }

    override suspend fun getBMI(): String {
        val bmi = sharedPreferenceDataHandler.getBMI().toString()
        if(bmi == ""){
            setBMI()
        }
        return bmi
    }


    override fun setTrainingSplit(splitId: String): Result {
        sharedPreferenceDataHandler.saveSplitId(splitId)
        return Result.SUCCESS
    }

    override suspend fun getTrainingSplit(): SplitDetails? {
        val splitId = sharedPreferenceDataHandler.getSplitId() ?: return null
        val defaultSplits = mapOf(
            "split1" to DefaultSplits.split1,
            "split2" to DefaultSplits.split2,
            "split3" to DefaultSplits.split3,
            "split4" to DefaultSplits.split4
        )
        return defaultSplits[splitId] ?: getSplitById.execute(splitId)
    }

}