package com.gymxy.gymxyone.presentation.composableFunctions

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.data.offline.SharedPreferenceCollectionName
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.domain.helperFunctions.millisToDate
import com.gymxy.gymxyone.domain.helperFunctions.millisecondsToTime
import com.gymxy.gymxyone.domain.helperFunctions.unitConversionWeight
import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.EachExerciseReps
import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.ui.theme.gradient1
import com.gymxy.gymxyone.ui.theme.gradient2
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.gymxy.gymxyone.ui.theme.mainTextColor
import com.gymxy.gymxyone.ui.theme.secondaryTextColor

@Preview(showBackground = true , backgroundColor = 0L)
@Composable
fun PerformedDaysList() {
    val sampleData = PerformedDays(
        splitId = "1",
        splitDayName = "Monday Workout",
        startTime = 1700000000000,
        endTime = 1700003600000,
        rating = 4.5,
        exerciseDetails = mapOf(
            1 to EachExercisePerformedDetails(
                exerciseName = "Push-ups",
                details = listOf(
                    EachExerciseReps(weight = 10000, reps = 15),
                    EachExerciseReps(weight = 10000, reps = 20)
                )
            ),
            2 to EachExercisePerformedDetails(
                exerciseName = "Bench Press",
                details = listOf(
                    EachExerciseReps(weight = 510000, reps = 10),
                    EachExerciseReps(weight = 510005, reps = 8),
                    EachExerciseReps(weight = 601000, reps = 6)
                )
            ),
            3 to EachExercisePerformedDetails(
                exerciseName = "Bench Press",
                details = listOf(
                    EachExerciseReps(weight = 510000, reps = 10),
                    EachExerciseReps(weight = 510005, reps = 8),
                    EachExerciseReps(weight = 601000, reps = 6)
                )
            ),
            4 to EachExercisePerformedDetails(
                exerciseName = "Bench Press",
                details = listOf(
                    EachExerciseReps(weight = 510000, reps = 10),
                    EachExerciseReps(weight = 510005, reps = 8),
                    EachExerciseReps(weight = 601000, reps = 6)
                )
            )
        )
    )

    LazyColumn {
        item {
            PerformedDaysItem(sampleData)
            PerformedDaysItem(sampleData)
            PerformedDaysItem(sampleData)
            PerformedDaysItem(sampleData)
            PerformedDaysItem(sampleData)

        }
    }
}


@Composable
@Preview(showBackground = true)
fun PerformedDaysItem(
    performedDays: PerformedDays = PerformedDays(),
    weightUnit: String = SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM,
    deleteFunction: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Day Name and Time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = performedDays.splitDayName, fontWeight = FontWeight.ExtraBold,
                color = mainTextColor,
                fontSize = 20.sp
            )
            Text(
                text = "${millisecondsToTime(performedDays.startTime)} - ${
                    millisecondsToTime(
                        performedDays.endTime
                    )
                }",
                color = mainTextColor,
                fontSize = 16.sp
            )
        }

        // Ratings and Delete Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = millisToDate(performedDays.startTime),
                    color = mainTextColor,
                    fontSize = 16.sp
                )

                Text(
                    text = "Ratings: ${performedDays.rating} / 5",
                    color = mainTextColor,
                    fontSize = 16.sp
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_outline_24),
                contentDescription = null,
                tint = Color.Black,      //mainTextColor,
                modifier = Modifier
                    .clickable {
                        deleteFunction()
                    }
                    .padding(end = 20.dp)
                    .drawBehind {
                        drawRoundRect(
                            brush = gradientBrush,
                            alpha = 1f,
                            cornerRadius = CornerRadius(x = 10.0f, y = 10.0f)
                        )
                    }
            )
        }

        // Exercise Details (Expandable Section)
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Exercise Details", fontWeight = FontWeight.Bold,
                    color = mainTextColor,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                performedDays.exerciseDetails.forEach { (setNumber, exerciseDetails) ->
                    Text(
                        text = "Exercise $setNumber:    ${exerciseDetails.exerciseName}",
                        fontWeight = FontWeight.SemiBold,
                        color = mainTextColor,
                        fontSize = 16.sp
                    )

                    exerciseDetails.details.forEachIndexed { index, reps ->
                        Text(
                            text = "         Set ${index + 1}:     ${
                                reps.reps.toString().padStart(2, ' ')
                            } reps of ${unitConversionWeight(weightUnit, reps.weight)}",
                            color = mainTextColor,
                            fontSize = 15.sp
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = if (isExpanded) R.drawable.baseline_arrow_drop_up_24 else R.drawable.baseline_arrow_drop_down_24),
                contentDescription = null, tint = Color.Black,
                modifier = Modifier
                    .clickable {
                        isExpanded = !isExpanded
                    }
                    .drawBehind {
                        drawCircle(
                            brush = gradientBrush,
                            alpha = 1f
                        )
                    }

            )

        }


    }
}
