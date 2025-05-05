package com.gymxy.gymxyone.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.gymxy.gymxyone.domain.helperFunctions.SortingHelper
import com.gymxy.gymxyone.domain.helperFunctions.mapToList
import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.DeletePerformedDay
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetPerformedDays
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNameAndUrlFromSP
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetTrainingSplit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getTrainingSplit: GetTrainingSplit,
    private val deletePerformedDay: DeletePerformedDay,
    private val sortingHelper: SortingHelper,
    private val getPerformedDays: GetPerformedDays,
    private val getNameAndUrlFromSP: GetNameAndUrlFromSP
) : ViewModel() {
    // states

    private val _list = MutableStateFlow<List<PerformedDays>>(emptyList())
    val list: StateFlow<List<PerformedDays>> get() = _list
    suspend fun getListOfPerformedDays() {
        val tempList = getPerformedDays.execute()
        for (i in tempList!!) {
            _list.value += i
        }
    }

    private val _photoUrl = MutableStateFlow("")
    val photoUrl: StateFlow<String> get() = _photoUrl
    private fun setPhotoUrl(url: String) {
        _photoUrl.value = url
    }

    private val _selectedSplitDetails = MutableStateFlow(SplitDetails())
    val selectedSplitDetails: StateFlow<SplitDetails> get() = _selectedSplitDetails
    suspend fun getTrainingSplit() {
        _selectedSplitDetails.value = getTrainingSplit.execute()!!
    }

    fun getWorkoutDayTypes(): List<String> {
        return mapToList(_selectedSplitDetails.value.details)
    }

    private val _listOfSortByTypes = MutableStateFlow<List<String>>(emptyList())
    private val listOfSortByTypes: StateFlow<List<String>> get() = _listOfSortByTypes
    private fun setListOfSortByTypes(list: List<String>) {
        _listOfSortByTypes.value = list
    }

    fun getListOfSortByTypes(): List<String> {
        setListOfSortByTypes(getWorkoutDayTypes())
        _listOfSortByTypes.value =
            listOfSortByTypes.value.plus("Sort By Time Ascending").plus("Sort By Time Descending")
        return listOfSortByTypes.value
    }

    fun sortBy(option: String) {
        when (val index = _listOfSortByTypes.value.indexOf(option)) {
            listOfSortByTypes.value.size - 2 -> {
                sortByTimeAscending()
            }
            listOfSortByTypes.value.size - 1 -> {
                sortByTimeDescending()
            }
            else -> {
                CoroutineScope(Dispatchers.Default).launch {
                    sortByDays(index)
                }
            }
        }
    }


    suspend fun deletePerformedDay(performedDays: PerformedDays) {
        deletePerformedDay.execute(performedDays)
    }

    /**
     * @fun sortByDays
     * @param index is the index of the day in the training split
     */
    private suspend fun sortByDays(index: Int) {
        _list.value = sortingHelper.sortByDays(index, list.value)
    }

    private fun sortByTimeAscending() {
        _list.value = sortingHelper.sortByTimeAscending(list.value)
    }

    private fun sortByTimeDescending() {
        _list.value = sortingHelper.sortByTimeDescending(list.value)
    }

    fun getGreetings(): String {
        val pair = getNameAndUrlFromSP.execute()
        setPhotoUrl(pair.second)
        val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return if (currentHour < 12) {
            "Good Morning " + pair.first
        } else if (currentHour < 17) {
            "Good Afternoon " + pair.first
        } else {
            "Good Evening " + pair.first
        }
    }

//    fun addNewWorkout(indexOfSplit : Int){
//        viewModel.startTimer()
//        viewModel.setSplitDayName(_selectedSplitDetails.value.details[indexOfSplit]!!)
//        viewModel.setSplitId(_selectedSplitDetails.value.splitId)
//        viewModel.setToShowThisInFeed(true)
//        viewModel.setRating(0.0)
//    }


}