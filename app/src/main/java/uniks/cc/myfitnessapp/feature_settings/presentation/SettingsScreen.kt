package uniks.cc.myfitnessapp.feature_settings.presentation

import android.graphics.drawable.*
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }

    val isErrorHeight = remember { mutableStateOf(false) }
    val isErrorWeight = remember { mutableStateOf(false) }

    val pickedDate = remember { mutableStateOf(LocalDate.now()) }

    val formattedDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate.value)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    fun validateHeight(value: Int) {
        isErrorHeight.value = value !in 0..300
        println(isErrorHeight.value)
    }

    fun validateWeight(value: Int) {
        isErrorWeight.value = value !in 0..400
        println(isErrorWeight.value)
    }

    Column(
        modifier = Modifier
            .background(color = Color.Gray)
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = height.value,
                onValueChange = {
                    height.value = it
                    if (it != "") validateHeight(it.toInt())
                },
                placeholder = {
                    Text(
                        text = "Height",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                suffix = { Text(text = "cm") },
                trailingIcon = {
                    if (isErrorHeight.value)
                        Icon(Icons.Filled.Error, "error", tint = Color.Red)
                },
                singleLine = true,
                isError = isErrorHeight.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (isErrorHeight.value) {
                Text(
                    text = "Not a real Height",
                    color = Color.Red,
                    //style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = weight.value,
                onValueChange = {
                    weight.value = it
                    if (it != "") validateWeight(it.toInt())
                },
                placeholder = {
                    Text(
                        text = "Weight",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                suffix = { Text(text = "kg") },
                trailingIcon = {
                    if (isErrorWeight.value)
                        Icon(Icons.Filled.Error, "error", tint = Color.Red)
                },
                singleLine = true,
                isError = isErrorWeight.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (isErrorWeight.value) {
                Text(
                    text = "Not a real Weight",
                    color = Color.Red,
                    //style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Button(
                onClick = {
                    dateDialogState.show()
                },
            ) {
                Text(text = "Pick birth date")
            }
            val context = LocalContext.current
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                        Toast.makeText(
                            context,
                            "Clicked ok",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                    allowedDateValidator = {
                        it.dayOfMonth % 2 == 1
                    }
                ) {
                    pickedDate.value = it
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SetPreview() {
    SettingsScreen()
}