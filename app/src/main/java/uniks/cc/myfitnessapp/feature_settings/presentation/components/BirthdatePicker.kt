package uniks.cc.myfitnessapp.feature_settings.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun BirthdatePicker(
    pickedDate: MutableState<LocalDateTime>,
) {
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
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
    Button(
        onClick = {
            dateDialogState.show()
        },
        modifier = Modifier.padding(4.dp)
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
            initialDate = pickedDate.value.toLocalDate(),
            title = "Pick a date",
        ) {
            pickedDate.value = LocalDateTime.of(it, LocalTime.now())
        }
    }
}