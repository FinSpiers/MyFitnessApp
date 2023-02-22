package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.feature_settings.presentation.components.BirthdatePicker
import uniks.cc.myfitnessapp.feature_settings.presentation.components.GenderPicker
import uniks.cc.myfitnessapp.feature_settings.presentation.components.HealthField
import uniks.cc.myfitnessapp.feature_settings.presentation.components.WeightField

@Composable
fun BodyMeasurements() {

    val focusManager = LocalFocusManager.current
    val interactionSource = MutableInteractionSource()

    Column(
        modifier = Modifier
            .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HealthField()

        WeightField()

        GenderPicker()

        BirthdatePicker()
    }
}