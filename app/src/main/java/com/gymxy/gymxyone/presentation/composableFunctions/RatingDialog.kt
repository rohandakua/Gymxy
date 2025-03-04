package com.gymxy.gymxyone.presentation.composableFunctions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gymxy.gymxyone.domain.helperFunctions.provideMainTextStyle
import com.gymxy.gymxyone.ui.theme.mainCardBackground
import com.gymxy.gymxyone.ui.theme.mainTextColor

@Composable
@Preview
fun RatingDialog(
    onDismiss: () -> Unit = {},
    onSave: (Double) -> Unit = { _ -> }
) {
    var rating by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { NormalText(text = "How would you rate your workout ?", textSize = 24) },
        text = {
            Column {
                OutlinedTextField(
                    value = rating,
                    onValueChange = {
                        rating = it
                        val doubleValue = it.toDoubleOrNull()
                        errorText = if (doubleValue == null || doubleValue !in 0.0..5.0) {
                            "Enter a valid rating between 0.0 and 5.0"
                        } else {
                            null
                        }
                    },
                    label = { NormalText(text = "Rating (0.0 - 5.0)", textSize = 16) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = errorText != null,
                    textStyle = provideMainTextStyle(24),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = mainTextColor,
                        unfocusedTextColor = mainTextColor,
                        focusedContainerColor = mainCardBackground,
                        unfocusedContainerColor = mainCardBackground,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = mainTextColor,
                        errorContainerColor = mainCardBackground,
                        errorCursorColor = mainTextColor,
                        errorTextColor = Color.Red,
                        errorIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                errorText?.let {
                    Text(it, color = Color.Red, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {

            NormalText(text = "Save", textSize = 24, modifier = Modifier.clickable {
                val doubleValue = rating.toDoubleOrNull()
                if (doubleValue != null && doubleValue in 0.0..5.0) {
                    onSave(doubleValue)
                    onDismiss()
                } else {
                    errorText = "Enter a valid rating between 0.0 and 5.0"
                }
            })

        },
        dismissButton = {
            NormalText(
                text = "Dismiss",
                textSize = 24,
                modifier = Modifier.clickable { onDismiss() })

        },
        containerColor = mainCardBackground
    )
}
