package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
fun WeatherBox(
    borderStroke: Dp,
    currentTemp: Int,
    imageVector: ImageVector,
    isWeatherGood: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                borderStroke,
                MaterialTheme.colorScheme.primary,
                MaterialTheme.shapes.small
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .border(
                    borderStroke,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.shapes.small
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentWeatherBox(temperature = currentTemp, imageVector = imageVector)
        }
        ActivityPlanner(isWeatherGood = isWeatherGood)
    }
}