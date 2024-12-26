package com.gymxy.gymxyone.domain.models

/**
 * splitId is same as SplitDetails , email followed by the no. of custom splits
 * splitDayName is the name of the split day in the SplitDetails' detail map
 * toShowThisInFeed is used to manage the deletion of the PerformedDays. if true then this is not deleted , else it is deleted and do not show in the feed.
 *
 * **IMPORTANT**
 * exerciseDetails is a map of {0-> {object of EachExercisePerformedDetails},1-> {object of EachExercisePerformedDetails}}
 */
data class PerformedDays (
    var splitId : String = "",
    var splitDayName : String = "Split Day Name",
    var startTime : Long = System.currentTimeMillis(),
    var endTime : Long = System.currentTimeMillis(),
    var rating : Double = 0.0,
    var toShowThisInFeed : Boolean = true,
    var exerciseDetails : Map<Int, EachExercisePerformedDetails> = mapOf()
    )
