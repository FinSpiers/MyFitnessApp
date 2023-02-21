package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BirthdatePicker() {

    val pickedDate = remember { mutableStateOf(LocalDate.now()) }

    val formattedDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate.value)
        }
    }

    val dateDialogState = rememberMaterialDialogState()


    Text(
        text = "Birthdate: " + formattedDate.value,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
    Button(
        onClick = {
            dateDialogState.show()
        },
    ) {
        Text(text = "Pick birth date")
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
        ) {
            pickedDate.value = it
        }
    }
}