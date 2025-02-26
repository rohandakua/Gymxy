package com.gymxy.gymxyone.presentation.screens

import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.domain.helperFunctions.gradientBrush
import com.gymxy.gymxyone.domain.helperFunctions.provideMainTextStyle
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.presentation.composableFunctions.NormalText
import com.gymxy.gymxyone.ui.theme.gradient1
import com.gymxy.gymxyone.ui.theme.gradient2
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.gymxy.gymxyone.ui.theme.mainCardBackground
import com.gymxy.gymxyone.ui.theme.mainTextColor
import com.gymxy.gymxyone.ui.theme.secondaryTextColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


@Composable
@Preview(widthDp = 960, heightDp = 540)
fun PreviewSettingPage(

) {
    SettingPage()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SettingPage(

) {
    var expanded by remember { mutableStateOf(false) }
    val options =
        mutableListOf(
            "Bro Split",
            "Push Pull Legs",
            "abc def why not text"
        )  // change the options TODO
    // this options should have a "add new Split" option , so i am adding a new option in the list
    // if this is clicked then user should be forwarded to add new split
    options.add("Add new split")
    var selectedOption by remember { mutableStateOf(options[3]) }

    val orientation =
        LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    var ratingSwitch by remember {
        mutableStateOf(true)
    }
    var reminderSwitch by remember {
        mutableStateOf(true)
    }

    var weight by remember {
        mutableStateOf("100")
    }
    var expandedWeightUnit by remember { mutableStateOf(false) }
    val optionsWeightUnit = listOf("kgs", "lbs")
    var selectedOptionWeightUnit by remember { mutableStateOf(optionsWeightUnit[0]) }
    var showTimePicker by remember {
        mutableStateOf(false)
    }
    var height by remember {
        mutableStateOf("180")
    }
    var heightFt by remember {
        mutableStateOf("5")
    }
    var heightIn by remember {
        mutableStateOf("11")
    }
    var timePickerState = rememberTimePickerState()

    var expandedHeightUnit by remember { mutableStateOf(false) }
    val optionsHeightUnit = listOf("cms", "ft & in")

    var selectedOptionHeightUnit by remember { mutableStateOf(optionsHeightUnit[1]) }

    var splitDetails = remember {      // from viewmodel
        mutableStateOf(SplitDetails())
    }
    var newSplitName by remember {
        mutableStateOf("")
    }
    var mapOfDay = remember {
        mutableStateMapOf<Int, String>()
    }
    var splitDayName by remember {
        mutableStateOf("")
    }


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
    if (orientation) { // for portrait

        Box(modifier = Modifier.safeDrawingPadding()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // for title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.13f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Gymxy Settings", textSize = 27)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "add new workout",
                            tint = Color.Black,
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush,
                                        alpha = 1f,
                                        radius = this.size.minDimension / 2 + 10
                                    )
                                }
                                .size(25.dp)
                                .clickable {}

                        )
                        NormalText(text = "Logout", textSize = 14)
                    }
                }

                // for setting of split
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Change Split", textSize = 20)
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.fillMaxWidth(.6f)
                    ) {
                        OutlinedTextField(
                            value = selectedOption,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded,
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            brush = gradientBrush, alpha = 1f
                                        )
                                    })
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor

                            ),
                            textStyle = provideMainTextStyle(16),
                            maxLines = 2,
                            minLines = 2
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = Color.Black
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(text = {
                                    NormalText(
                                        text = option,
                                        textSize = 15
                                    )
                                }, onClick = {
                                    selectedOption = option
                                    expanded = false
                                })
                            }
                        }
                    }

                }

                // for having rating or not
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Rating after workout", textSize = 20)
                    Switch(checked = ratingSwitch, onCheckedChange = {
                        ratingSwitch = it
                    }, thumbContent = {
                        Icon(painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                            contentDescription = "check",
                            tint = Color.Black,
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush, alpha = 1f
                                    )
                                }
                                .size(25.dp)
                                .clickable {
                                    ratingSwitch = !ratingSwitch
                                })
                    }, colors = SwitchDefaults.colors(
                        checkedTrackColor = secondaryTextColor,
                        uncheckedTrackColor = mainCardBackground
                    )

                    )
                }
                // for setting weight unit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Change weight unit", textSize = 20)

                    ExposedDropdownMenuBox(
                        expanded = expandedWeightUnit,
                        onExpandedChange = { expandedWeightUnit = it },
                        modifier = Modifier.fillMaxWidth(.7f)
                    ) {
                        OutlinedTextField(
                            value = selectedOptionWeightUnit,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded,
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            brush = gradientBrush, alpha = 1f
                                        )
                                    })
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor

                            ),
                            textStyle = provideMainTextStyle(16),
                            maxLines = 1,
                            minLines = 1
                        )

                        ExposedDropdownMenu(
                            expanded = expandedWeightUnit,
                            onDismissRequest = { expandedWeightUnit = false },
                            containerColor = Color.Black
                        ) {
                            optionsWeightUnit.forEach { option ->
                                DropdownMenuItem(text = {
                                    NormalText(
                                        text = option,
                                        textSize = 15
                                    )
                                }, onClick = {
                                    selectedOptionWeightUnit = option
                                    expandedWeightUnit = false
                                })
                            }
                        }
                    }
                }

                // for setting weight
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Update weight", textSize = 20)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = weight, onValueChange = { weight = it },
                            textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor,
                                cursorColor = mainTextColor,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.fillMaxWidth(.4f),
                            maxLines = 1,
                            minLines = 1,
                        )
                        NormalText(text = selectedOptionWeightUnit, textSize = 20)
                    }

                }


                // for setting height unit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Change height unit", textSize = 20)

                    ExposedDropdownMenuBox(
                        expanded = expandedHeightUnit,
                        onExpandedChange = { expandedHeightUnit = it },
                        modifier = Modifier.fillMaxWidth(.7f)
                    ) {
                        OutlinedTextField(
                            value = selectedOptionHeightUnit,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded,
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            brush = gradientBrush, alpha = 1f
                                        )
                                    })
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor

                            ),
                            textStyle = provideMainTextStyle(16),
                            maxLines = 1,
                            minLines = 1
                        )

                        ExposedDropdownMenu(
                            expanded = expandedHeightUnit,
                            onDismissRequest = { expandedHeightUnit = false },
                            containerColor = Color.Black
                        ) {
                            optionsHeightUnit.forEach { option ->
                                DropdownMenuItem(text = {
                                    NormalText(
                                        text = option,
                                        textSize = 15
                                    )
                                }, onClick = {
                                    selectedOptionHeightUnit = option
                                    expandedHeightUnit = false
                                })
                            }
                        }
                    }

                }


                // for setting height
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Update height", textSize = 20)
                    if (selectedOptionHeightUnit == "cms") {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = height, onValueChange = { height = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.fillMaxWidth(.4f),
                                maxLines = 1,
                                minLines = 1,
                            )
                            NormalText(text = selectedOptionHeightUnit, textSize = 20)
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = heightFt, onValueChange = { heightFt = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.width(60.dp),
                                maxLines = 1,
                                minLines = 1,
                            )
                            NormalText(text = "ft", textSize = 20)
                            TextField(
                                value = heightIn, onValueChange = { heightIn = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.width(70.dp),
                                maxLines = 1,
                                minLines = 1,
                            )
                            NormalText(text = "in", textSize = 20)
                        }
                    }

                }

                // for BMI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "BMI", textSize = 20)
                    NormalText(text = "30.0", textSize = 20)
                    NormalText(text = "Overweight", textSize = 20)
                }

                // for Reminder
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Reminder Alert", textSize = 20)
                    Switch(checked = reminderSwitch, onCheckedChange = {
                        reminderSwitch = it
                    }, thumbContent = {
                        Icon(painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                            contentDescription = "check",
                            tint = Color.Black,
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush, alpha = 1f
                                    )
                                }
                                .size(25.dp)
                                .clickable {
                                    reminderSwitch = !reminderSwitch
                                })
                    }, colors = SwitchDefaults.colors(
                        checkedTrackColor = secondaryTextColor,
                        uncheckedTrackColor = mainCardBackground
                    )

                    )
                }


                // for time
                if (reminderSwitch) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NormalText(text = "Reminder Time", textSize = 20)
                        NormalText(
//                            text = hourOfDay.toString().format()
//                                .padStart(2, '0') + " : " + minute.toString().format()
//                                .padStart(2, '0'),
                            text = timePickerState.hour.toString().format()
                                .padStart(2, '0') + " : " + timePickerState.minute.toString()
                                .format()
                                .padStart(2, '0'),
                            textSize = 24,
                            modifier = Modifier
                                .clickable {
                                    showTimePicker = !showTimePicker
                                }
                                .padding(end = 20.dp)
                        )


                    }
                }


            }


        }

        if (showTimePicker) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp, start = 60.dp, end = 60.dp, bottom = 180.dp)
                    .background(color = mainCardBackground, shape = RoundedCornerShape(40.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(mainBackgroundColor)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(
                        state = timePickerState,
                        layoutType = TimePickerDefaults.layoutType(),
                        colors = TimePickerDefaults.colors(
                            clockDialColor = mainBackgroundColor,
                            clockDialUnselectedContentColor = secondaryTextColor,
                            clockDialSelectedContentColor = mainTextColor,
                            periodSelectorSelectedContentColor = mainTextColor,
                            periodSelectorBorderColor = Color.Black,
                            periodSelectorUnselectedContainerColor = secondaryTextColor,
                            periodSelectorSelectedContainerColor = secondaryTextColor,
                            periodSelectorUnselectedContentColor = Color.Black,
                            timeSelectorSelectedContainerColor = secondaryTextColor,
                            timeSelectorUnselectedContentColor = Color.Black,
                            timeSelectorSelectedContentColor = mainTextColor,
                            timeSelectorUnselectedContainerColor = secondaryTextColor,
                            selectorColor = Color.DarkGray
                        ),

                        )
                    NormalText(text = "Set", textSize = 32, modifier = Modifier.clickable {
                        showTimePicker = !showTimePicker
                    })
                }
            }
        }
        if (selectedOption == "Add new split") {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp, start = 60.dp, end = 60.dp, bottom = 180.dp)
                    .background(color = mainCardBackground, shape = RoundedCornerShape(40.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(mainBackgroundColor)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NormalText(text = "Add new split", textSize = 32)

                    TextField(
                        value = newSplitName, onValueChange = { newSplitName = it },
                        textStyle = provideMainTextStyle(26).copy(textAlign = TextAlign.Center),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedLabelColor = Color.Transparent,
                            unfocusedLabelColor = Color.Transparent,
                            focusedTextColor = mainTextColor,
                            unfocusedTextColor = mainTextColor,
                            cursorColor = mainTextColor,
                            focusedIndicatorColor = mainCardBackground,
                            unfocusedIndicatorColor = mainCardBackground,
                            disabledIndicatorColor = Color.Transparent,
                            focusedPlaceholderColor = Color.Transparent,
                            unfocusedPlaceholderColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(top = 20.dp, bottom = 10.dp),
                        maxLines = 1,
                        minLines = 1,
                        label = {
                            NormalText(text = "Split name", textSize = 14)
                        }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(top = 10.dp, bottom = 20.dp),
                    ) {
                        items(mapOfDay.toList()) { item ->

                            NormalText(
                                text = "Day " + (item.first).toString() + " : " + item.second,
                                textSize = 24
                            )

                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            TextField(
                                value = splitDayName,
                                onValueChange = { splitDayName = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.fillMaxWidth(.6f),
                                maxLines = 1,
                                minLines = 1,
                                label = {
                                    NormalText(text = "Day name", textSize = 16)
                                }
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                val context = LocalContext.current
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                                    contentDescription = "Add new day", tint = Color.Black,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .drawBehind {
                                            drawCircle(
                                                brush = gradientBrush,
                                                alpha = 1f
                                            )
                                        }
                                        .clickable {
                                            if (mapOfDay.size == 7) {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Maximum 7 days allowed",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            } else {
                                                mapOfDay[mapOfDay.size + 1] = splitDayName
                                                splitDayName = ""
                                            }

                                        }

                                )

                                NormalText(text = "Add new day", textSize = 10)
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NormalText(text = "Save", textSize = 25, modifier = Modifier
                                .clickable {
                                    //TODO add logic here for saving the split
                                }

                            )
                            NormalText(text = "Cancel", textSize = 25, modifier = Modifier
                                .clickable {
                                    //TODO add logic here for saving the split
                                    selectedOption = options[0]
                                }

                            )
                        }

                    }


                }
            }
        }

    } else {              // for potrait
        //for all the content
        Box(modifier = Modifier.safeDrawingPadding()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // for title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.13f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Gymxy Settings", textSize = 27)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "add new workout",
                            tint = Color.Black,
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush,
                                        alpha = 1f,
                                        radius = this.size.minDimension / 2 + 10
                                    )
                                }
                                .size(25.dp)
                                .clickable {}

                        )
                        NormalText(text = "Logout", textSize = 14)
                    }
                }

                // for setting of split
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Change Split", textSize = 20)
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.fillMaxWidth(.6f)
                    ) {
                        OutlinedTextField(
                            value = selectedOption,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded,
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            brush = gradientBrush, alpha = 1f
                                        )
                                    })
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor

                            ),
                            textStyle = provideMainTextStyle(16),
                            maxLines = 2,
                            minLines = 2
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = Color.Black
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(text = {
                                    NormalText(
                                        text = option,
                                        textSize = 15
                                    )
                                }, onClick = {
                                    selectedOption = option
                                    expanded = false
                                })
                            }
                        }
                    }

                }

                // for having rating or not
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Rating after workout", textSize = 20)
                    Switch(checked = ratingSwitch, onCheckedChange = {
                        ratingSwitch = it
                    }, thumbContent = {
                        Icon(painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                            contentDescription = "check",
                            tint = Color.Black,
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush, alpha = 1f
                                    )
                                }
                                .size(25.dp)
                                .clickable {
                                    ratingSwitch = !ratingSwitch
                                })
                    }, colors = SwitchDefaults.colors(
                        checkedTrackColor = secondaryTextColor,
                        uncheckedTrackColor = mainCardBackground
                    )

                    )
                }
                // for setting weight unit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Change weight unit", textSize = 20)

                    ExposedDropdownMenuBox(
                        expanded = expandedWeightUnit,
                        onExpandedChange = { expandedWeightUnit = it },
                        modifier = Modifier.fillMaxWidth(.7f)
                    ) {
                        OutlinedTextField(
                            value = selectedOptionWeightUnit,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded,
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            brush = gradientBrush, alpha = 1f
                                        )
                                    })
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor

                            ),
                            textStyle = provideMainTextStyle(16),
                            maxLines = 1,
                            minLines = 1
                        )

                        ExposedDropdownMenu(
                            expanded = expandedWeightUnit,
                            onDismissRequest = { expandedWeightUnit = false },
                            containerColor = Color.Black
                        ) {
                            optionsWeightUnit.forEach { option ->
                                DropdownMenuItem(text = {
                                    NormalText(
                                        text = option,
                                        textSize = 15
                                    )
                                }, onClick = {
                                    selectedOptionWeightUnit = option
                                    expandedWeightUnit = false
                                })
                            }
                        }
                    }
                }

                // for setting weight
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Update weight", textSize = 20)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = weight, onValueChange = { weight = it },
                            textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor,
                                cursorColor = mainTextColor,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.fillMaxWidth(.4f),
                            maxLines = 1,
                            minLines = 1,
                        )
                        NormalText(text = selectedOptionWeightUnit, textSize = 20)
                    }

                }


                // for setting height unit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Change height unit", textSize = 20)

                    ExposedDropdownMenuBox(
                        expanded = expandedHeightUnit,
                        onExpandedChange = { expandedHeightUnit = it },
                        modifier = Modifier.fillMaxWidth(.7f)
                    ) {
                        OutlinedTextField(
                            value = selectedOptionHeightUnit,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded,
                                    modifier = Modifier.drawBehind {
                                        drawCircle(
                                            brush = gradientBrush, alpha = 1f
                                        )
                                    })
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent,
                                focusedIndicatorColor = mainCardBackground,
                                unfocusedIndicatorColor = mainCardBackground,
                                disabledIndicatorColor = Color.Transparent,
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                focusedTextColor = mainTextColor,
                                unfocusedTextColor = mainTextColor

                            ),
                            textStyle = provideMainTextStyle(16),
                            maxLines = 1,
                            minLines = 1
                        )

                        ExposedDropdownMenu(
                            expanded = expandedHeightUnit,
                            onDismissRequest = { expandedHeightUnit = false },
                            containerColor = Color.Black
                        ) {
                            optionsHeightUnit.forEach { option ->
                                DropdownMenuItem(text = {
                                    NormalText(
                                        text = option,
                                        textSize = 15
                                    )
                                }, onClick = {
                                    selectedOptionHeightUnit = option
                                    expandedHeightUnit = false
                                })
                            }
                        }
                    }

                }


                // for setting height
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Update height", textSize = 20)
                    if (selectedOptionHeightUnit == "cms") {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = height, onValueChange = { height = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.fillMaxWidth(.4f),
                                maxLines = 1,
                                minLines = 1,
                            )
                            NormalText(text = selectedOptionHeightUnit, textSize = 20)
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = heightFt, onValueChange = { heightFt = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.width(60.dp),
                                maxLines = 1,
                                minLines = 1,
                            )
                            NormalText(text = "ft", textSize = 20)
                            TextField(
                                value = heightIn, onValueChange = { heightIn = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.width(70.dp),
                                maxLines = 1,
                                minLines = 1,
                            )
                            NormalText(text = "in", textSize = 20)
                        }
                    }

                }

                // for BMI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "BMI", textSize = 20)
                    NormalText(text = "30.0", textSize = 20)
                    NormalText(text = "Overweight", textSize = 20)
                }

                // for Reminder
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalText(text = "Reminder Alert", textSize = 20)
                    Switch(checked = reminderSwitch, onCheckedChange = {
                        reminderSwitch = it
                    }, thumbContent = {
                        Icon(painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                            contentDescription = "check",
                            tint = Color.Black,
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        brush = gradientBrush, alpha = 1f
                                    )
                                }
                                .size(25.dp)
                                .clickable {
                                    reminderSwitch = !reminderSwitch
                                })
                    }, colors = SwitchDefaults.colors(
                        checkedTrackColor = secondaryTextColor,
                        uncheckedTrackColor = mainCardBackground
                    )

                    )
                }


                // for time
                if (reminderSwitch) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NormalText(text = "Reminder Time", textSize = 20)
                        NormalText(
//                            text = hourOfDay.toString().format()
//                                .padStart(2, '0') + " : " + minute.toString().format()
//                                .padStart(2, '0'),
                            text = timePickerState.hour.toString().format()
                                .padStart(2, '0') + " : " + timePickerState.minute.toString()
                                .format()
                                .padStart(2, '0'),
                            textSize = 24,
                            modifier = Modifier
                                .clickable {
                                    showTimePicker = !showTimePicker
                                }
                                .padding(end = 20.dp)
                        )


                    }
                }


            }


        }
        if (showTimePicker) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp, start = 60.dp, end = 60.dp, bottom = 180.dp)
                    .background(color = mainCardBackground, shape = RoundedCornerShape(40.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(mainBackgroundColor)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(
                        state = timePickerState,
                        layoutType = TimePickerDefaults.layoutType(),
                        colors = TimePickerDefaults.colors(
                            clockDialColor = mainBackgroundColor,
                            clockDialUnselectedContentColor = secondaryTextColor,
                            clockDialSelectedContentColor = mainTextColor,
                            periodSelectorSelectedContentColor = mainTextColor,
                            periodSelectorBorderColor = Color.Black,
                            periodSelectorUnselectedContainerColor = secondaryTextColor,
                            periodSelectorSelectedContainerColor = secondaryTextColor,
                            periodSelectorUnselectedContentColor = Color.Black,
                            timeSelectorSelectedContainerColor = secondaryTextColor,
                            timeSelectorUnselectedContentColor = Color.Black,
                            timeSelectorSelectedContentColor = mainTextColor,
                            timeSelectorUnselectedContainerColor = secondaryTextColor,
                            selectorColor = Color.DarkGray
                        ),

                        )
                    NormalText(text = "Set", textSize = 32, modifier = Modifier.clickable {
                        showTimePicker = !showTimePicker
                    })
                }
            }
        }
        if (selectedOption == "Add new split") {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, start = 80.dp, end = 80.dp, bottom = 40.dp)
                    .background(color = mainCardBackground, shape = RoundedCornerShape(40.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(mainBackgroundColor)
                        .padding( start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NormalText(text = "Add new split", textSize = 28)

                    TextField(
                        value = newSplitName, onValueChange = { newSplitName = it },
                        textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedLabelColor = Color.Transparent,
                            unfocusedLabelColor = Color.Transparent,
                            focusedTextColor = mainTextColor,
                            unfocusedTextColor = mainTextColor,
                            cursorColor = mainTextColor,
                            focusedIndicatorColor = mainCardBackground,
                            unfocusedIndicatorColor = mainCardBackground,
                            disabledIndicatorColor = Color.Transparent,
                            focusedPlaceholderColor = Color.Transparent,
                            unfocusedPlaceholderColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(top = 10.dp),
                        maxLines = 1,
                        minLines = 1,
                        label = {
                            NormalText(text = "Split name", textSize = 18)
                        }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(.9f).fillMaxHeight(.6f)
                            .padding(top = 10.dp, bottom = 20.dp),
                    ) {
                        items(mapOfDay.toList()) { item ->

                            NormalText(
                                text = "Day " + (item.first).toString() + " : " + item.second,
                                textSize = 20
                            )

                        }
                    }

//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Top,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
                        Row {
                            TextField(
                                value = splitDayName,
                                onValueChange = { splitDayName = it },
                                textStyle = provideMainTextStyle(20).copy(textAlign = TextAlign.Center),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedLabelColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedTextColor = mainTextColor,
                                    unfocusedTextColor = mainTextColor,
                                    cursorColor = mainTextColor,
                                    focusedIndicatorColor = mainCardBackground,
                                    unfocusedIndicatorColor = mainCardBackground,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = Color.Transparent,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.fillMaxWidth(.6f),
                                maxLines = 1,
                                minLines = 1,
                                label = {
                                    NormalText(text = "Day name", textSize = 18)
                                }
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                val context = LocalContext.current
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                                    contentDescription = "Add new day", tint = Color.Black,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .drawBehind {
                                            drawCircle(
                                                brush = gradientBrush,
                                                alpha = 1f
                                            )
                                        }
                                        .clickable {
                                            if (mapOfDay.size == 7) {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Maximum 7 days allowed",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            } else {
                                                mapOfDay[mapOfDay.size + 1] = splitDayName
                                                splitDayName = ""
                                            }

                                        }

                                )

                                NormalText(text = "Add new day", textSize = 10)
                            }
//                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.Top
                        ) {
                            NormalText(text = "Save", textSize = 22, modifier = Modifier
                                .clickable {
                                    //TODO add logic here for saving the split
                                }

                            )
                            NormalText(text = "Cancel", textSize = 22, modifier = Modifier
                                .clickable {
                                    //TODO add logic here for saving the split
                                    selectedOption = options[0]
                                }

                            )
                        }

                    }


                }
            }
        }

    }


}

