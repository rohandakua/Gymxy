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
    onDismiss: () -> Unit={},
    onConfirm: (String, String) -> Unit={_,_->},
    weightUnit: String = "kg",
) {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }

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
                        value = firstNumber,
                        onValueChange = { firstNumber = it },
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
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = mainTextColor,
                            unfocusedTextColor = mainTextColor,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = mainTextColor,
                            unfocusedIndicatorColor = mainTextColor
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = secondNumber,
                        onValueChange = { secondNumber = it },
                        label = { NormalText(text = "Repetitions", textSize = 18) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onConfirm(firstNumber,secondNumber) }
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = LexendDeca,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.focusRequester(secondFocusRequester),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = mainTextColor,
                            unfocusedTextColor = mainTextColor,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = mainTextColor,
                            unfocusedIndicatorColor = mainTextColor
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { onConfirm(firstNumber, secondNumber) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mainCardBackground,
                        disabledContainerColor = mainCardBackground
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


