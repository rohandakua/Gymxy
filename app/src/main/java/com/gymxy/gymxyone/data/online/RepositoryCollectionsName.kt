package com.gymxy.gymxyone.data.online

object RepositoryCollectionsName {
    /**
     * all the data is store in the users collection in firestore
     */
    const val FIREBASE_COLLECTION_BASE_ADDRESS = "users"

    /**
     * all the data of performed days is store in the performedDays collection in firestore
     */
    const val FIREBASE_COLLECTION_PERFORMED_DAYS = "performedDays"

    /**
     * all the data related to weight is store in the weightDetails collection in firestore
     */
    const val FIREBASE_COLLECTION_WEIGHT_DETAILS = "weightDetails"

    /**
     * all the data related to height is store in the heightDetails collection in firestore
     */
    const val FIREBASE_COLLECTION_HEIGHT_DETAILS = "heightDetails"

    /**
     * all the data related to split is store in the splitDetails collection in firestore
     */
    const val FIREBASE_COLLECTION_SPLIT_DETAILS = "splitDetails"

    /**
     * all the exercise details of a particular performed day is stored in performedExerciseDetails collection
     * It is a subCollection of exercise in a particular performed day
     */
    const val FIREBASE_COLLECTION_EXERCISE_DETAILS = "performedExerciseDetails"
}