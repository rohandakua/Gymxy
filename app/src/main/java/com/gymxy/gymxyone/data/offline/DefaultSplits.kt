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
        splitName = "Push Pull Legs",
        details = mapOf(
            1 to "Push Day",
            2 to "Pull Day",
            3 to "Legs Day"
        )
    )
    val split3 = SplitDetails(
        splitId = "split3",
        splitName = "Push Pull Legs",
        details = mapOf(
            1 to "Push Day",
            2 to "Pull Day",
            3 to "Legs Day"
        )
    )
    val split4 = SplitDetails(
        splitId = "split4",
        splitName = "Push Pull Legs",
        details = mapOf(
            1 to "Push Day",
            2 to "Pull Day",
            3 to "Legs Day"
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