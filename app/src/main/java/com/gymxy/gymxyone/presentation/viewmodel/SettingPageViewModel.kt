package com.gymxy.gymxyone.presentation.viewmodel

import android.provider.DocumentsContract.Root
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymxy.gymxyone.data.offline.GymExerciseCollection
import com.gymxy.gymxyone.domain.helperFunctions.cmsToFeetInches
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveSplit
import com.gymxy.gymxyone.domain.useCases.googleAuthUseCase.Logout
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetBMI
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetHeight
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetHeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNameAndUrlFromSP
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationTime
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetRaingPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetTrainingSplit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetWeight
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetWeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveHeightSetting
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveHeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveNotificationPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveNotificationTime
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveRatingPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveWeightSetting
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveWeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SetTrainingSplit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SettingPageViewModel @Inject constructor(
    /**
     * This are setting usecases
     */
    private val getBMI: GetBMI,
    private val getRaingPermission: GetRaingPermission,
    private val getWeightUnit: GetWeightUnit,
    private val getHeightUnit: GetHeightUnit,
    private val getTrainingSplit: GetTrainingSplit,
    private val getNotificationTime: GetNotificationTime,
    private val getNotificationPermission: GetNotificationPermission,
    private val saveHeightSetting: SaveHeightSetting,
    private val saveHeightUnit: SaveHeightUnit,
    private val saveNotificationPermission: SaveNotificationPermission,
    private val saveNotificationTime: SaveNotificationTime,
    private val saveRatingPermission: SaveRatingPermission,
    private val saveWeightSetting: SaveWeightSetting,
    private val saveWeightUnit: SaveWeightUnit,
    private val setTrainingSplit: SetTrainingSplit,
    private val getHeight: GetHeight,
    private val getWeight: GetWeight,
    private val getNameAndUrlFromSP: GetNameAndUrlFromSP,

    /**
     *  This are firestore Usecase
     */
    private val saveSplit: SaveSplit,

    /**
     * This are google auth usecase
     */
    private val authViewModel: AuthViewModel
) : ViewModel() {
    // States
    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> get() = _name
    fun setName(name: String ){
        _name.value = name
    }

    private val _url = MutableStateFlow<String>("")
    val url: StateFlow<String> get() = _url
    fun setUrl(url: String ){
        _url.value = url
    }
    fun setNameUrl(){
        val nameAndUrl = getNameAndUrlFromSP.execute()
        setUrl(nameAndUrl.second)
        setName(nameAndUrl.first)
    }

    private val _trainingSplitSelected = MutableStateFlow<String>("")
    val trainingSplitSelected: StateFlow<String> get() = _trainingSplitSelected
    fun setTrainingSplitSelected(splitId: String): Result {
        _trainingSplitSelected.value = splitId
        return setTrainingSplit.execute(splitId)
    }

    private val _notificationPermission = MutableStateFlow<Boolean>(false)
    val notificationPermission: StateFlow<Boolean> get() = _notificationPermission
    fun setNotificationPermission(permission: Boolean): Result {
        _notificationPermission.value = permission
        return saveNotificationPermission.execute(permission)
    }

    private val _ratingPermission = MutableStateFlow<Boolean>(false)
    val ratingPermission: StateFlow<Boolean> get() = _ratingPermission
    fun setRatingPermission(permission: Boolean): Result {
        _ratingPermission.value = permission
        return saveRatingPermission.execute(permission)
    }

    private val _notificationTime = MutableStateFlow<Long>(System.currentTimeMillis())
    val notificationTime: StateFlow<Long> get() = _notificationTime
    fun setNotificationTime(time: Long): Result {
        _notificationTime.value = time
        return saveNotificationTime.execute(time)
    }

    /**
     *   const val SHARED_PREFERENCE_KILOGRAM = "kilogram"
     *   const val SHARED_PREFERENCE_POUND = "pound"
     */
    private val _weightUnit = MutableStateFlow<String>("")
    val weightUnit: StateFlow<String> get() = _weightUnit
    fun setWeightUnit(unit: String): Result {
        _weightUnit.value = unit
        return saveWeightUnit.execute(unit)
    }

    /**
     *
     *     const val SHARED_PREFERENCE_CENTIMETER = "centimeter"
     *     const val SHARED_PREFERENCE_FEET_AND_INCHES = "feet"
     */
    private val _heightUnit = MutableStateFlow<String>("")
    val heightUnit: StateFlow<String> get() = _heightUnit
    fun setHeightUnit(unit: String): Result {
        _heightUnit.value = unit
        return saveHeightUnit.execute(unit)
    }

    private val _weight = MutableStateFlow<String>("")
    val weight: StateFlow<String> get() = _weight
    suspend fun setWeight(weight: String): Result {
        _weight.value = weight
        if (weight.toDoubleOrNull() != null) {
            return saveWeightSetting.execute(weight.toDouble())
        }
        return Result.FAILURE
    }

    private val _heightCms = MutableStateFlow<String>("")
    val heightCms: StateFlow<String> get() = _heightCms
    suspend fun setHeightCms(height: String): Result {
        if (height.toDoubleOrNull() != null) {
            _heightCms.value = height
            return saveHeightSetting.execute(height = height.toDouble())
        }
        return Result.FAILURE
    }

    private val _heightFt = MutableStateFlow<String>("")
    val heightFt: StateFlow<String> get() = _heightFt
    private val _heightInch = MutableStateFlow<String>("")
    val heightInch: StateFlow<String> get() = _heightInch
    suspend fun setHeight(feet: String, inch: String): Result {
        if (feet.toDoubleOrNull() != null && inch.toDoubleOrNull() != null) {
            _heightFt.value = feet
            _heightInch.value = inch
            return saveHeightSetting.execute(feet = feet.toDouble(), inch = inch.toDouble())
        }
        return Result.FAILURE
    }

    private val _bmi = MutableStateFlow<Double>(0.0)
    val bmi: StateFlow<Double> get() = _bmi
    suspend fun setBMI() {
        _bmi.value = getBMI.execute().toDouble()
    }

    private val _newSplit = MutableStateFlow<SplitDetails>(SplitDetails())
    val newSplit: StateFlow<SplitDetails> get() = _newSplit
    suspend fun setNewSplit(splitDetails: SplitDetails) {
        _newSplit.value = splitDetails
    }


    init {
        viewModelScope.launch {
            getTrainingSplit.execute()?.let { setTrainingSplitSelected(splitId = it.splitId) }
            setNotificationPermission(permission = getNotificationPermission.execute())
            setRatingPermission(permission = getRaingPermission.execute())
            setNotificationTime(time = getNotificationTime.execute())
            /**
             *   const val SHARED_PREFERENCE_KILOGRAM = "kilogram"
             *   const val SHARED_PREFERENCE_POUND = "pound"
             */
            setWeightUnit(unit = getWeightUnit.execute())

            /**
             *
             *     const val SHARED_PREFERENCE_CENTIMETER = "centimeter"
             *     const val SHARED_PREFERENCE_FEET_AND_INCHES = "feet"
             */
            setHeightUnit(unit = getHeightUnit.execute())
            setWeight(getWeight.execute().toString())
            val ht = getHeight.execute()
            setHeightCms(height = ht.toString())
            val ftIn = cmsToFeetInches(ht)
            setHeight(feet = ftIn.first.toString(), inch = ftIn.second.toString())
            setBMI()
        }
    }

    suspend fun logout(): Result {
        return authViewModel.logout()
    }

    suspend fun saveNewSplit (): Result {
        return saveSplit.execute(_newSplit.value)
    }

    private val _exerciseList = MutableStateFlow<List<String>>(GymExerciseCollection.exerciseList)
    val exerciseList : StateFlow<List<String>> get() = _exerciseList


    fun addNewExercise(exercise:String, category: String){  // FROM ExerciseCategory
        GymExerciseCollection.addExercise(category = category.uppercase(), exerciseName = exercise.uppercase())
        _exerciseList.value = GymExerciseCollection.exerciseList
    }

    fun deleteExercise(exercise:String){
        GymExerciseCollection.deleteExercise(exerciseName = exercise.uppercase())
        _exerciseList.value = GymExerciseCollection.exerciseList
    }


}