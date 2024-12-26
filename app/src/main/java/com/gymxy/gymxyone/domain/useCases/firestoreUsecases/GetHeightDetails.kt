package com.gymxy.gymxyone.domain.useCases.firestoreUsecases

import com.gymxy.gymxyone.domain.models.Height
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface
import javax.inject.Inject

class GetHeightDetails @Inject constructor(
    private var firestoreDataHandler: FirestoreDataHandlingInterface
) {
    suspend fun execute(
    ) : List<Height>? {
        return firestoreDataHandler.getHeightDetails()
    }
}