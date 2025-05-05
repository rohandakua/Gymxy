package com.gymxy.gymxyone.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.presentation.composableFunctions.AddExerciseItem
import com.gymxy.gymxyone.presentation.composableFunctions.NormalText
import com.gymxy.gymxyone.presentation.composableFunctions.RatingDialog
import com.gymxy.gymxyone.presentation.viewmodel.ExercisePageViewModel
import com.gymxy.gymxyone.presentation.viewmodel.SettingPageViewModel
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.gymxy.gymxyone.ui.theme.mainCardBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SaveExercisePage(
    modifier: Modifier,
    navController: NavHostController,
    exercisePageViewModel: ExercisePageViewModel = hiltViewModel(),
    settingPageViewModel: SettingPageViewModel = hiltViewModel()
) {
    var showForRating = settingPageViewModel.ratingPermission.collectAsState()
    val orientation =
        LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val dayName = exercisePageViewModel.splitDayName.collectAsState()
    val exerciseDetails = exercisePageViewModel.exerciseDetails.collectAsState()
    val timeElapsed = exercisePageViewModel.timeElapsed.collectAsState()
    var toShowRatingDialog = false
    val context = LocalContext.current
    var addNewExerciseClicked = false
    var listOfExercises: List<String>
    var selectedExercise by remember { mutableStateOf<String?>(null) }
    var weightUnit = settingPageViewModel.weightUnit.collectAsState()
    var toShowAddNewRep = false

    Box(
        modifier = modifier
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


        // TO show the rating dialog after the exercise is finished
        if (showForRating.value && toShowRatingDialog) {
            RatingDialog(
                onDismiss = {
                    toShowRatingDialog = false
                },
                onSave = {
                    CoroutineScope(Dispatchers.Default).launch {
                        exercisePageViewModel.setRating(it)
                        val result = async { exercisePageViewModel.endExercise() }.await()
                        if (result == Result.SUCCESS) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Saved the data.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            navController.popBackStack()
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Failed to store the data. Please try after sometime.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            )
        }
        // To show list of the exercises
        if (addNewExerciseClicked) {
            listOfExercises = exercisePageViewModel.getExerciseNames()
                .toList() // this is to always fetch the updated exercises
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, end = 60.dp, top = 150.dp, bottom = 200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DropdownMenu(
                    expanded = addNewExerciseClicked,
                    onDismissRequest = { addNewExerciseClicked = false },
                    shape = RoundedCornerShape(20.dp),
                    containerColor = mainBackgroundColor
                ) {
                    listOfExercises.forEach { exerciseName ->
                        DropdownMenuItem(
                            text = { NormalText(text = exerciseName, textSize = 24) },
                            onClick = {
                                selectedExercise = exerciseName
                                exercisePageViewModel.addNewExercise(selectedExercise!!)
                                addNewExerciseClicked = false
                            },
                            modifier = Modifier.background(mainBackgroundColor)

                        )
                    }
                }

            }
        }

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
                NormalText(text = dayName.value, textSize = 20)
                NormalText(text = timeElapsed.value, textSize = 25)
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight(if (orientation) .88f else .75f)
                    .fillMaxWidth()
                    .padding(horizontal = if (orientation) 4.dp else 10.dp)
            ) {
                if (exerciseDetails.value.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NormalText(text = "No Exercise Added ", textSize = 24)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        items(exerciseDetails.value.toList()) {
                            AddExerciseItem(
                                eachExercisePerformedDetails = it.second,
                                addNewSet = { index, weight, reps, weightUnit ->
                                    exercisePageViewModel.addReps(index, weight, reps, weightUnit)
                                },
                                weightUnit = weightUnit.value,
                                index = it.first

                            )
                        }
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
                        .clickable {
                            toShowRatingDialog = true
                        }

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
                            .clickable {
                                // for adding new exercise
                                // opening a select box for choosing the exercise
                                addNewExerciseClicked = true
                            }
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
