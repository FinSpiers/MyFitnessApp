package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ActivityPlanner(isWeatherGood: Boolean) {
    Column(
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val motivationText = if(isWeatherGood) "Good weather,\nyou might wanna go outside!" else "Better train inside,\nnot looking too good outside!"
        Text(text = "Activity planner", style = MaterialTheme.typography.titleMedium)
        Text(text = motivationText, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
    }
}

