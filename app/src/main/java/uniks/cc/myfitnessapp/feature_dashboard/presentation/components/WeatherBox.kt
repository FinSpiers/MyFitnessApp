package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.R

@Composable
fun WeatherBox(
    currentTemp: Int,
    isWeatherGood: Boolean,
    currentWeatherMain: String
) {
    val resId: Int = when (currentWeatherMain) {
        "Clear" -> R.drawable.image_weather_sunny
        "Clouds" -> R.drawable.image_weather_clouds
        "Rain" -> R.drawable.image_weather_rain
        "Snow" -> R.drawable.image_weather_snow
        "Extreme" -> R.drawable.image_weather_extreme
        "Sun" -> R.drawable.image_weather_sunny
        "Fog" -> R.drawable.image_weather_fog
        "Drizzle" -> R.drawable.image_weather_drizzle
        else -> R.drawable.image_weather_mixed_cloudy_sunny_rainy
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, MaterialTheme.shapes.small)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentWeatherBox(temperature = currentTemp, resId = resId)
        }
        ActivityPlanner(isWeatherGood = isWeatherGood)
    }
}