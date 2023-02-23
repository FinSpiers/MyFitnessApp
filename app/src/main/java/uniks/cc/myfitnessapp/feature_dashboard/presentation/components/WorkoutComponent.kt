package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.feature_dashboard.data.WorkoutInfoText
import java.time.format.DateTimeFormatter

@Composable
fun WorkoutComponent(workoutInfoText: WorkoutInfoText, onClick: () -> Unit) {

    val pattern = "dd-MM-yyyy HH:mm"

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(5.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = workoutInfoText.image),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
            contentDescription = workoutInfoText.workoutName,
            modifier = Modifier.size(80.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = workoutInfoText.workoutDate.format(DateTimeFormatter.ofPattern(pattern)))
            Text(text = workoutInfoText.workoutInfo)
        }
    }

}
