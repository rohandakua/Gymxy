package com.gymxy.gymxyone.domain.useCases.firestoreUsecases

import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface
import javax.inject.Inject

class SavePerformedDay @Inject constructor(
    private var firestoreDataHandler: FirestoreDataHandlingInterface
) {
    suspend fun execute(
        performedDays: PerformedDays,
        exerciseDetails: Map<Int, EachExercisePerformedDetails>
    ): Result {
        return firestoreDataHandler.addPerformedDays(
            performedDays = performedDays,
            exerciseDetails = exerciseDetails
        )

    }

}