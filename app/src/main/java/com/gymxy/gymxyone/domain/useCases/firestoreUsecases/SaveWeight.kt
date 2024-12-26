package com.gymxy.gymxyone.domain.useCases.firestoreUsecases

import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.Weight
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface
import javax.inject.Inject

class SaveWeight @Inject constructor(
    private var firestoreDataHandler: FirestoreDataHandlingInterface
) {
    suspend fun execute(
        weightDetails: Weight
    ) : Result {
        return firestoreDataHandler.addWeight(weightDetails = weightDetails)
    }
}