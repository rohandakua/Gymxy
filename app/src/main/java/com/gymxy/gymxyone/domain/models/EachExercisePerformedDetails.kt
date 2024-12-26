package com.gymxy.gymxyone.domain.models
data class EachExercisePerformedDetails  (
    var exerciseName: String = "exercise name",
    var details : List<EachExerciseReps> = listOf()
)