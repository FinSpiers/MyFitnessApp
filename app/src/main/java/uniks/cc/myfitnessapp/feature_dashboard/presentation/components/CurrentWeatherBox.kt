package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CurrentWeatherBox(temperature: Int, resId: Int) {
    Column(modifier = Modifier.padding(4.dp)) {
        Text(text = "Current weather", style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = resId),
                contentDescription = "current weather",
                modifier = Modifier.size(32.dp).padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$temperature °C")
        }
    }
}