package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun BodyMeasurements() {

    val focusManager = LocalFocusManager.current
    val interactionSource = MutableInteractionSource()

    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .padding(top = 15.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HeightField()

        WeightField()
        
        Spacer(modifier = Modifier.height(15.dp))
        
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth(.75f)
                .fillMaxHeight(.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderPicker()

            BirthdatePicker()
        }
    }
}