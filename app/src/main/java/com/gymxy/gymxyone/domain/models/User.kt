package com.gymxy.gymxyone.domain.models

/**
 * userId of the user is the email of the user , name is the name of the user
 *
 * **IMPORTANT**
 * in firestore this has 4 subCollections in this document
 * 1. performedDays which has documents in form of PerformedDays
 * 2. weightDetails which has documents in form of Weight
 * 3. heightDetails which has documents in form of Height
 * 4. splitDetails which has documents in form of SplitDetails
 *
 * make sure to retrieve all these
 */
data class User (
    var userId : String = "",
    var name : String = "name",
    var noOfCustomSplit : Int = 0
)