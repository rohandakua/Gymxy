package com.gymxy.gymxyone.data.offline

import com.gymxy.gymxyone.domain.models.SplitDetails

object DefaultSplits {
    val split1 = SplitDetails(
        splitId = "split1",
        splitName = "Push Pull Legs",
        details = mapOf(
            1 to "Push Day",
            2 to "Pull Day",
            3 to "Legs Day"
        )
    )

    val split2 = SplitDetails(
        splitId = "split2",
        splitName = "Upper Lower Split",
        details = mapOf(
            1 to "Upper Body A",
            2 to "Lower Body A",
            3 to "Upper Body B",
            4 to "Lower Body B"
        )
    )

    val split3 = SplitDetails(
        splitId = "split3",
        splitName = "Full Body Split",
        details = mapOf(
            1 to "Full Body A",
            2 to "Full Body B",
            3 to "Full Body C"
        )
    )

    val split4 = SplitDetails(
        splitId = "split4",
        splitName = "Bro Split",
        details = mapOf(
            1 to "Chest Day",
            2 to "Back Day",
            3 to "Legs Day",
            4 to "Shoulders Day",
            5 to "Arms Day"
        )
    )
}

/**
 * @property AddNewSplitItem is for making the user add a new split , it is only to be used by the
 * system for identifying the user actions and nothing else. It is not to be modified and touched.
 */
object AddNewSplitItem {
    val split by lazy { SplitDetails(splitName = addNewSplit, splitId = "addNewSplitSystemDefault", details = mapOf()) }
    const val addNewSplit = "*** Add New Split ***"
}