package com.gymxy.gymxyone.presentation.composableFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.gymxy.gymxyone.ui.theme.LexendDeca
import com.gymxy.gymxyone.ui.theme.mainBackgroundColor
import com.gymxy.gymxyone.ui.theme.mainCardBackground
import com.gymxy.gymxyone.ui.theme.mainTextColor

@Composable
@Preview
fun EnterSetDetailsDialog(
    showDialog: Boolean = true,
    onDismiss: () -> Unit = {},
    /**
     * @param onConfirm
     * This onConfirm takes a int, double, int for (index, weight, reps) and should convert the weight into desired
     * unit and then call the save function from the view model
     */
    onConfirm: (Int, Double, Int, String) -> Unit = { _, _, _, _ -> },
    weightUnit: String = "kg",
    index: Int = 1 // TODO remove this
) {
    var weight by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf<String?>(null) }
    var repsError by remember { mutableStateOf<String?>(null) }

    val firstFocusRequester = remember { FocusRequester() }
    val secondFocusRequester = remember { FocusRequester() }

    // Request focus on the first input field when dialog opens
    LaunchedEffect(showDialog) {
        if (showDialog) {
            firstFocusRequester.requestFocus()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { NormalText(text = "Enter Exercise Details", textSize = 24) },
            text = {
                Column {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = {
                            weight = it
                            weightError =
                                if (it.toDoubleOrNull() == null || it.toDoubleOrNull()!! <= 0) {
                                    "Enter a valid weight"
                                } else {
                                    null
                                }
                        },
                        label = { NormalText(text = "Weight in $weightUnit", textSize = 18) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { secondFocusRequester.requestFocus() }
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = LexendDeca,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.focusRequester(firstFocusRequester),
                        isError = weightError != null,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = mainTextColor,
                            unfocusedTextColor = mainTextColor,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = if (weightError != null) Color.Red else mainTextColor,
                            unfocusedIndicatorColor = if (weightError != null) Color.Red else mainTextColor
                        )
                    )
                    weightError?.let {
                        Text(it, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = reps,
                        onValueChange = {
                            reps = it
                            repsError = if (it.toIntOrNull() == null || it.toIntOrNull()!! <= 0) {
                                "Enter a valid number of reps"
                            } else {
                                null
                            }
                        },
                        label = { NormalText(text = "Repetitions", textSize = 18) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                val weightValue = weight.toDoubleOrNull()
                                val repsValue = reps.toIntOrNull()

                                if (weightValue != null && weightValue > 0 && repsValue != null && repsValue > 0) {
                                    onConfirm(
                                        index,
                                        weightValue, repsValue, weightUnit
                                    )
                                    onDismiss()
                                } else {
                                    weightError = "Enter a valid weight"
                                    repsError = "Enter a valid number of reps"
                                }
                            }
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = LexendDeca,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.focusRequester(secondFocusRequester),
                        isError = repsError != null,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = mainTextColor,
                            unfocusedTextColor = mainTextColor,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = if (repsError != null) Color.Red else mainTextColor,
                            unfocusedIndicatorColor = if (repsError != null) Color.Red else mainTextColor
                        )
                    )
                    repsError?.let {
                        Text(it, color = Color.Red, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val weightValue = weight.toDoubleOrNull()
                        val repsValue = reps.toIntOrNull()

                        if (weightValue != null && weightValue > 0 && repsValue != null && repsValue > 0) {
                            onConfirm(index, weightValue, repsValue,weightUnit)
                            onDismiss()
                        } else {
                            weightError = "Enter a valid weight"
                            repsError = "Enter a valid number of reps"
                        }
                    },
                    enabled = weight.toDoubleOrNull() != null && weight.toDoubleOrNull()!! > 0 &&
                            reps.toIntOrNull() != null && reps.toIntOrNull()!! > 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mainCardBackground,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    NormalText(text = "OK", textSize = 18)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mainCardBackground,
                        disabledContainerColor = mainCardBackground
                    )
                ) {
                    NormalText(text = "Cancel", textSize = 18)
                }
            },
            containerColor = mainBackgroundColor,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        )
    }
}
