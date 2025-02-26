package com.gymxy.gymxyone.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.gymxy.gymxyone.data.offline.GymExerciseCollection
import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.EachExerciseReps
import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SavePerformedDay
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.GetFormattedTime
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.ResetStopwatch
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.StartStopwatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisePageViewModel @Inject constructor(
    private val getFormattedTime: GetFormattedTime,
    private val resetStopwatch: ResetStopwatch,
    private val startStopwatch: StartStopwatch,
    private val savePerformedDay: SavePerformedDay,
) : ViewModel() {
    //State
        private var stopwatchRunning = false
    fun startTimer() {
        stopwatchRunning = true
        setStartTime(System.currentTimeMillis())
        startStopwatch.execute()
        CoroutineScope(Dispatchers.IO).launch {
            while (!stopwatchRunning) {
                delay(10000)
                setTimeElapsed(getFormattedTime.execute())
            }
        }
    }

    fun endTimer() {
        stopwatchRunning = false
        resetStopwatch.execute()
    }

    private val _timeElapsed = MutableStateFlow<String>("00:00")
    val timeElapsed: StateFlow<String> get() = _timeElapsed
    fun setTimeElapsed(time: String) {
        _timeElapsed.value = time
    }

    /*
     var splitId : String = "",
    var splitDayName : String = "Split Day Name",
    var startTime : Long = System.currentTimeMillis(),
    var endTime : Long = System.currentTimeMillis(),
    var rating : Double = 0.0,
    var toShowThisInFeed : Boolean = true,
    var exerciseDetails : Map<Int, EachExercisePerformedDetails> = mapOf()

    var exerciseName: String = "exercise name",
    var details : List<EachExerciseReps> = listOf()
     */
    private val _splitId = MutableStateFlow("")
    val splitId: StateFlow<String> get() = _splitId
    fun setSplitId(newSplitId: String) {
        _splitId.value = newSplitId
    }

    private val _splitDayName = MutableStateFlow("Split Day Name")
    val splitDayName: StateFlow<String> get() = _splitDayName
    fun setSplitDayName(newSplitDayName: String) {
        _splitDayName.value = newSplitDayName
    }

    private val _startTime = MutableStateFlow(System.currentTimeMillis())
    val startTime: StateFlow<Long> get() = _startTime
    fun setStartTime(newStartTime: Long) {
        _startTime.value = newStartTime
    }

    private val _endTime = MutableStateFlow(System.currentTimeMillis())
    val endTime: StateFlow<Long> get() = _endTime
    fun setEndTime(newEndTime: Long) {
        _endTime.value = newEndTime
    }

    private val _rating = MutableStateFlow(0.0)
    val rating: StateFlow<Double> get() = _rating
    fun setRating(newRating: Double) {
        _rating.value = newRating
    }

    private val _toShowThisInFeed = MutableStateFlow(true)
    val toShowThisInFeed: StateFlow<Boolean> get() = _toShowThisInFeed
    fun setToShowThisInFeed(show: Boolean) {
        _toShowThisInFeed.value = show
    }

    private var index = 0

    private val _exerciseDetails = MutableStateFlow<Map<Int, EachExercisePerformedDetails>>(mapOf())
    val exerciseDetails: StateFlow<Map<Int, EachExercisePerformedDetails>> get() = _exerciseDetails
    fun setExerciseDetails(newDetails: Map<Int, EachExercisePerformedDetails>) {
        _exerciseDetails.value = newDetails
    }

    /**
     * demo data
     * val sampleExerciseDetails = mapOf(
     *     1 to EachExercisePerformedDetails(
     *         exerciseName = "Bench Press",
     *         details = listOf(
     *             EachExerciseReps(weight = 50, reps = 10),
     *             EachExerciseReps(weight = 60, reps = 8),
     *             EachExerciseReps(weight = 70, reps = 6)
     *         )
     *     ),
     *     2 to EachExercisePerformedDetails(
     *         exerciseName = "Squats",
     *         details = listOf(
     *             EachExerciseReps(weight = 80, reps = 12),
     *             EachExerciseReps(weight = 90, reps = 10),
     *             EachExerciseReps(weight = 100, reps = 8)
     *         )
     *     ),
     *     3 to EachExercisePerformedDetails(
     *         exerciseName = "Deadlift",
     *         details = listOf(
     *             EachExerciseReps(weight = 100, reps = 5),
     *             EachExerciseReps(weight = 110, reps = 4),
     *             EachExerciseReps(weight = 120, reps = 3)
     *         )
     *     )
     * )
     *
     *
     */

    fun addNewExercise(exerciseName: String) {
        setExerciseDetails(
            _exerciseDetails.value + (index++ to EachExercisePerformedDetails(
                exerciseName,
                listOf()
            ))
        )
    }

    /**
     * @fun addReps
     * it is adding new EachExerciseReps object to the list of EachExerciseReps objects
     * if state is not changing then make the use of setExerciseDetails
     * @param index = this is the index of the exercise in the exerciseDetails map
     */
    fun addReps(index: Int, weight: Long, reps: Int) {
        _exerciseDetails.value[index]!!.details += EachExerciseReps(weight, reps)
    }
    private fun clear() {
        // Reset each MutableStateFlow to its default value
        _splitId.value = ""
        _splitDayName.value = "Split Day Name"
        _startTime.value = System.currentTimeMillis()
        _endTime.value = System.currentTimeMillis()
        _rating.value = 0.0
        _toShowThisInFeed.value = true
        _exerciseDetails.value = mapOf()
        _timeElapsed.value = "00:00"
        index = 0
        stopwatchRunning = false
    }

    suspend fun endExercise(): Result {
        setEndTime(System.currentTimeMillis())
        endTimer()
        val result = savePerformedDay.execute(
            performedDays = PerformedDays(
                splitId = _splitId.value,
                splitDayName = _splitDayName.value,
                startTime = _startTime.value,
                endTime = _endTime.value,
                rating = _rating.value,
                toShowThisInFeed = true,
                exerciseDetails = _exerciseDetails.value
            ),
            exerciseDetails = _exerciseDetails.value
        )
        clear()
        return result
    }

    fun getExerciseNames(): MutableList<String> {
        return GymExerciseCollection.exerciseList
    }

    fun addNewWorkout(indexOfSplit : Int , splitDetails:SplitDetails){
        startTimer()
        setSplitDayName(splitDetails.details[indexOfSplit]!!)
        setSplitId(splitDetails.splitId)
        setToShowThisInFeed(true)
        setRating(0.0)
    }


}