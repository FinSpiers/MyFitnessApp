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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {

    val focusManager = LocalFocusManager.current
    val interactionSource = MutableInteractionSource()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(top = 20.dp)
            .clickable(interactionSource = interactionSource, indication = null)
            { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.Blue, shape = MaterialTheme.shapes.medium)
                .fillMaxWidth(0.73f)
                .padding(top = 22.dp, bottom = 22.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(interactionSource = interactionSource, indication = null)
                { focusManager.clearFocus() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BodyMeasurements()

            GoogleHealthConnect()

            ResetAppData()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetPreview() {
    SettingsScreen()
}