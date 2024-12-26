package com.gymxy.gymxyone.domain.useCases.firestoreUsecases

import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface
import javax.inject.Inject

class GetPerformedDays @Inject constructor(
    private var firestoreDataHandler: FirestoreDataHandlingInterface
) {
    suspend fun execute(
    ) : List<PerformedDays>? {
        return firestoreDataHandler.getPerformedDays()
    }
}