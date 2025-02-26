package com.gymxy.gymxyone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.EachExerciseReps
import com.gymxy.gymxyone.presentation.composableFunctions.AddExerciseItem
import com.gymxy.gymxyone.presentation.composableFunctions.NormalText
import com.gymxy.gymxyone.ui.theme.gradient1
import com.gymxy.gymxyone.ui.theme.gradient2
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.gymxy.gymxyone.ui.theme.mainCardBackground

@Preview
@Composable
fun SaveExercisePage(
    dayName: String = "Leg Workout",
    navController: NavController = rememberNavController()
) {
    val demo = mapOf(                       // by viewmodel.exerciseDetails
        1 to EachExercisePerformedDetails(
            exerciseName = "Bench Press",
            details = listOf(
                EachExerciseReps(weight = 50000, reps = 10),
                EachExerciseReps(weight = 60000, reps = 8),
                EachExerciseReps(weight = 70000, reps = 6),
                EachExerciseReps(weight = 80000, reps = 12),
                EachExerciseReps(weight = 90000, reps = 10),
                EachExerciseReps(weight = 100000, reps = 8)
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
        ),
        4 to EachExercisePerformedDetails(
            exerciseName = "Bench Press",
            details = listOf(
                EachExerciseReps(weight = 50000, reps = 10),
                EachExerciseReps(weight = 60000, reps = 8),
                EachExerciseReps(weight = 70000, reps = 6)
            )
        ),
        5 to EachExercisePerformedDetails(
            exerciseName = "Squats",
            details = listOf(
                EachExerciseReps(weight = 80000, reps = 12),
                EachExerciseReps(weight = 90000, reps = 10),
                EachExerciseReps(weight = 100000, reps = 8)
            )
        ),
        6 to EachExercisePerformedDetails(
            exerciseName = "Deadlift",
            details = listOf(
                EachExerciseReps(weight = 100000, reps = 5),
                EachExerciseReps(weight = 110000, reps = 4),
                EachExerciseReps(weight = 120000, reps = 3)
            )
        )
    )
    val orientation =
        LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainBackgroundColor)  // Color(0xFF10110f)
    ) {
        if (orientation) {
            Image(
                painter = painterResource(id = R.drawable.mainbackgroundsvg),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.landscapemainbackground),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }


    //for all the content
    Box(modifier = Modifier.safeDrawingPadding()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(if (orientation) .05f else .15f)
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 20.dp, start = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalText(text = dayName, textSize = 20)
                NormalText(text = "00:55 hrs", textSize = 25)
            }
            Box(modifier = Modifier
                .fillMaxHeight(if (orientation) .88f else .75f)
                .fillMaxWidth()
                .padding(horizontal = if (orientation) 4.dp else 10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(demo.values.toList()) {
                        AddExerciseItem(eachExercisePerformedDetails = it )
                    }
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 20.dp, top = 1.dp, bottom = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = mainCardBackground,
                        disabledContainerColor = mainBackgroundColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth(if (orientation) .4f else .3f)
                        .fillMaxHeight(if (orientation) .8f else .78f)

                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        NormalText(
                            text = "End",
                            textSize = 30,
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                        )
                    }
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxHeight(if (orientation) .8f else .9f)
                ) {

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

                    )

                    NormalText(text = "add new exercise", textSize = if (orientation) 15 else 15)
                }


            }


        }

    }
}
