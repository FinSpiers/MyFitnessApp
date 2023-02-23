package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.feature_settings.presentation.components.BodyMeasurements
import uniks.cc.myfitnessapp.feature_settings.presentation.components.GoogleHealthConnect
import uniks.cc.myfitnessapp.feature_settings.presentation.components.ResetAppData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.settingsState
    val focusManager = LocalFocusManager.current
    val interactionSource = MutableInteractionSource()

    val weight = remember { mutableStateOf("") }
    weight.value = state.value.weight.toString()

    val height = remember { mutableStateOf("") }
    height.value = state.value.height.toString()

    val isMale = remember { mutableStateOf(true) }
    isMale.value = state.value.isMale

    val pickedDate = remember { mutableStateOf(LocalDateTime.now()) }
    pickedDate.value = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(state.value.birthDateAsTimeStamp),
        TimeZone.getDefault().toZoneId()
    )

    fun saveBodyInfo() {
        viewModel.setBodyInfo(
            _weight = weight.value.toInt(),
            _height = height.value.toInt(),
            _isMale = isMale.value,
            _birthDateAsTimeStamp = pickedDate.value.toInstant(ZoneOffset.UTC).epochSecond
        )
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .fillMaxHeight(0.91f)
            .padding(top = 20.dp)
            .clickable(interactionSource = interactionSource, indication = null)
            { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BodyMeasurements(weight, height, isMale, pickedDate) {
            saveBodyInfo()
        }

        GoogleHealthConnect()

        ResetAppData(){
            viewModel.resetAllData()
        }
    }


}

@Preview(showBackground = true)
@Composable
fun SetPreview() {
    SettingsScreen()
}