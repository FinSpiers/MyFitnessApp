package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.MyFitnessApp
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun CurrentWeatherBox(temperature : Int, imageVector: ImageVector) {
    Column(modifier = Modifier.padding(4.dp)) {
        Text(text = "Current weather", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = imageVector, contentDescription = "current weather")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$temperature °C")
        }
    }
}