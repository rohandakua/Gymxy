package com.gymxy.gymxyone.domain.repositoryInterface

import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.Height
import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.models.User
import com.gymxy.gymxyone.domain.models.Weight

interface FirestoreDataHandlingInterface {
    suspend fun addPerformedDays(performedDays: PerformedDays,exerciseDetails : Map<Int, EachExercisePerformedDetails>) : Result
    suspend fun deletePerformedDays (performedDays: PerformedDays) : Result
    suspend fun addWeight (weightDetails: Weight) : Result
    suspend fun addHeight (heightDetails: Height) : Result
    suspend fun addSplit (splitDetails: SplitDetails) : Result
    suspend fun getNoOfCustomSplit () : Int?
    suspend fun setNoOfCustomSplit (valueToSet : Int) : Result
    suspend fun getWeightDetails () : List<Weight>?
    suspend fun getHeightDetails () : List<Height>?
    suspend fun getSplitDetails () : List<SplitDetails>?
    suspend fun getPerformedDays () : List<PerformedDays>?
    suspend fun getSplitById (splitId : String) : SplitDetails?


}