package com.gymxy.gymxyone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.presentation.composableFunctions.NormalText
import com.gymxy.gymxyone.presentation.composableFunctions.PerformedDaysItem
import com.gymxy.gymxyone.presentation.viewmodel.ExercisePageViewModel
import com.gymxy.gymxyone.presentation.viewmodel.HomePageViewModel
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    homePageViewModel: HomePageViewModel = hiltViewModel(),
    exercisePageViewModel: ExercisePageViewModel  = hiltViewModel()

) {
    val orientation =
        LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val list = homePageViewModel.list.collectAsState()

    val listOfDays = homePageViewModel.getWorkoutDayTypes()
    var expandedForDays by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf<String?>(null) }
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
        if(expandedForDays){
            LaunchedEffect (Unit){                          // To update the selected split details
                withContext(Dispatchers.IO){
                    homePageViewModel.getTrainingSplit()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, end = 60.dp, top = 150.dp, bottom = 200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DropdownMenu(
                    expanded = expandedForDays,
                    onDismissRequest = { expandedForDays = false },
                    shape = RoundedCornerShape(20.dp),
                    containerColor = mainBackgroundColor
                ) {
                    listOfDays.forEach { day ->
                        DropdownMenuItem(
                            text = { NormalText(text = day , textSize = 24) },
                            onClick = {
                                selectedDay = day
                                expandedForDays = false
                                exercisePageViewModel.addNewWorkout(listOfDays.indexOf(day),homePageViewModel.selectedSplitDetails.value)
                                navController.navigate("saveExercise")
                            },
                            modifier = Modifier.background(mainBackgroundColor)

                        )
                    }
                }

            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(  // for the title and setting icon
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalText(
                    text = homePageViewModel.getGreetings(),
                    textSize = 24
                )
                Spacer(modifier = Modifier.size(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clickable {
                            navController.navigate("settings")
                        }
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                brush = gradientBrush,
                                alpha = 1f,
                                blendMode = BlendMode.Multiply
                            )
                        }
                )


            }
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = if (orientation) 8.dp else 25.dp,
                        vertical = if (orientation) 4.dp else 4.dp
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(if (orientation) 0.9f else 0.65f)
            ) {
                if (list.value.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NormalText(text = "No Records Found", textSize = 30)
                    }
                } else {
                    LazyColumn {
                        items(list.value) {
                            PerformedDaysItem(it, deleteFunction = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    homePageViewModel.deletePerformedDay(
                                        performedDays = it
                                    )
                                }
                            })
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 1.dp, bottom = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var expanded by remember { mutableStateOf(false) }
                val options = homePageViewModel.getListOfSortByTypes()
                var selectedOption by remember { mutableStateOf(options[0]) }
                LaunchedEffect(
                    key1 = selectedOption
                ) {
                   homePageViewModel.sortBy(selectedOption)
                }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(.6f),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .menuAnchor(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            NormalText(text = "Sort By", textSize = 15)
                            NormalText(text = selectedOption, textSize = 20)
                        }
                        Icon(
                            painter = painterResource(id = if (expanded) R.drawable.baseline_arrow_drop_up_24 else R.drawable.baseline_arrow_drop_down_24),
                            contentDescription = null, tint = Color.Black,
                            modifier = Modifier
                                .menuAnchor()
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush,
                                        alpha = 1f
                                    )
                                }

                        )
                    }

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.Transparent),
                        containerColor = mainBackgroundColor
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { NormalText(text = option, textSize = 18) },
                                onClick = {
                                    selectedOption = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_circle_24),
                        contentDescription = "add new workout", tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                                expandedForDays = true
                            }
                            .drawBehind {
                                drawCircle(
                                    brush = gradientBrush,
                                    alpha = 1f
                                )
                            }

                    )


                    NormalText(text = "add new workout", textSize = if (orientation) 10 else 15)
                }


            }

        }

    }

}