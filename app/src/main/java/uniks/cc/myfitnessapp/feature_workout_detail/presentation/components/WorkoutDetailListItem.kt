package uniks.cc.myfitnessapp.feature_workout_detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WorkoutDetailListItem(title : String, value : String, unit : String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, modifier = Modifier.padding(start = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (unit != null) {
                Text(text = value, modifier = Modifier.padding(end = 4.dp))
                Text(text = unit, modifier = Modifier.padding(end = 8.dp))
            }
            else {
                Text(text = value, modifier = Modifier.padding(end = 8.dp))
            }

        }
    }
}