package com.gymxy.gymxyone.domain.useCases.firestoreUsecases

import com.gymxy.gymxyone.domain.models.Weight
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface
import javax.inject.Inject

class GetWeightDetails @Inject constructor(
    private var firestoreDataHandler: FirestoreDataHandlingInterface
) {
    suspend fun execute(
    ) : List<Weight>? {
        return firestoreDataHandler.getWeightDetails()
    }
}