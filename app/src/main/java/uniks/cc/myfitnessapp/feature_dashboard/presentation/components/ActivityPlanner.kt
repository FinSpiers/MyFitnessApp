package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.*
import uniks.cc.myfitnessapp.R
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ActivityPlanner(isWeatherGood: Boolean) {
    val resId = if (isWeatherGood) R.drawable.icon_weather_outside else R.drawable.icon_weather_inside
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val motivationText =
            if (isWeatherGood) "Good weather,\nyou might wanna go outside!" else "Better train inside,\nnot looking too good outside!"
        Column(modifier = Modifier.fillMaxWidth(0.85f), verticalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "Activity planner", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Text(
                text = motivationText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = resId), contentDescription = null, modifier = Modifier.size(32.dp).padding(end = 4.dp))
        }
    }
}

