package com.gymxy.gymxyone.presentation.composableFunctions

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.data.offline.SharedPreferenceCollectionName
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.domain.helperFunctions.unitConversionWeight
import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.EachExerciseReps
import com.gymxy.gymxyone.ui.theme.gradient1
import com.gymxy.gymxyone.ui.theme.gradient2
import com.gymxy.gymxyone.ui.theme.mainTextColor


@Composable
@Preview(showBackground = true, backgroundColor = 0L)
fun AddExerciseItem(
    eachExercisePerformedDetails: EachExercisePerformedDetails = EachExercisePerformedDetails(  // TODO remove this dummy data
        exerciseName = "Bench Press",
        details = listOf(
            EachExerciseReps(weight = 500000, reps = 10),
            EachExerciseReps(weight = 60000, reps = 8),
            EachExerciseReps(weight = 70000, reps = 6)
        )
    ),
    weightUnit: String = SharedPreferenceCollectionName.SHARED_PREFERENCE_KILOGRAM,
    addNewSet: () -> Unit = {}
) {

    var isExpanded by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var orientation = LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    /**
     * for adding new details , add the details in the demo object and make the viewmodel save the changes
     */
    // demo
    val demo = mapOf(                       // by viewmodel.exerciseDetails
        1 to EachExercisePerformedDetails(
            exerciseName = "Bench Press",
            details = listOf(
                EachExerciseReps(weight = 50000, reps = 10),
                EachExerciseReps(weight = 60000, reps = 8),
                EachExerciseReps(weight = 70000, reps = 6)
            )
        ),
        2 to EachExercisePerformedDetails(
            exerciseName = "Squats",
            details = listOf(
                EachExerciseReps(weight = 80000, reps = 12),
                EachExerciseReps(weight = 90000, reps = 10),
                EachExerciseReps(weight = 100000, reps = 8)
            )
        ),
        3 to EachExercisePerformedDetails(
            exerciseName = "Deadlift",
            details = listOf(
                EachExerciseReps(weight = 100000, reps = 5),
                EachExerciseReps(weight = 110000, reps = 4),
                EachExerciseReps(weight = 120000, reps = 3)
            )
        )
    )
    EnterSetDetailsDialog(showDialog=showDialog, onDismiss = {showDialog=false})

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
                text = eachExercisePerformedDetails.exerciseName, fontWeight = FontWeight.W900,
                color = mainTextColor,
                fontSize = 23.sp
            )
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.spacedBy(5.dp)){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                    contentDescription = "add new workout", tint = Color.Black,
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                brush = gradientBrush,
                                alpha = 1f
                            )
                        }
                        .size(25.dp)
                        .clickable {
                            showDialog = true
                        }

                )

                NormalText(text = "add new set", textSize = if(orientation) 13 else 15)
            }
        }
        //TODO in future add here the details of previous same day , in parallel
        if (isExpanded) {
            Column {
                eachExercisePerformedDetails.details.forEach { exerciseRep ->
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalText(
                            text = unitConversionWeight(weightUnit, exerciseRep.weight).padStart(length = 8, padChar = ' '),
                            textSize = 20,
                            modifier = Modifier.weight(1f)
                        )
                        NormalText(
                            text = "x".padStart(length = 2, padChar = ' '),
                            textSize = 20,
                            modifier = Modifier.weight(0.3f)
                        )
                        NormalText(
                            text = exerciseRep.reps.toString().padStart(length = 3, padChar = ' '),
                            textSize = 20,
                            modifier = Modifier.weight(0.5f)
                        )
                    }
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