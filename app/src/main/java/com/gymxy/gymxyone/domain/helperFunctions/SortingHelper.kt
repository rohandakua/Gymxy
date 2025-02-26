package com.gymxy.gymxyone.domain.helperFunctions

import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetTrainingSplit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SortingHelper @Inject constructor(
    private val getTrainingSplit: GetTrainingSplit
) {
    private lateinit var trainingSplit: SplitDetails
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            getTrainingSplit()
//        }
//    }
    private suspend fun getTrainingSplit(){
        trainingSplit = getTrainingSplit.execute()!!
    }
    suspend fun sortByDays(index : Int,list: List<PerformedDays>): List<PerformedDays> {
        getTrainingSplit()
        val dayName = trainingSplit.details[index]
        return list.filter { it.splitDayName == dayName }
            .sortedByDescending { it.startTime }
    }
    fun sortByTimeAscending(list: List<PerformedDays>): List<PerformedDays> {
        return list.sortedBy { it.startTime }
    }
    fun sortByTimeDescending(list: List<PerformedDays>): List<PerformedDays> {
        return list.sortedByDescending { it.startTime }

    }
}