package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WorkoutComponent(image: ImageVector, workoutName: String, workoutDate: LocalDateTime, workoutInfo: String) {

    val pattern = "dd-MM-yyyy HH:mm"

    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.medium)
        .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = image , contentDescription = workoutName)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = workoutDate.format(DateTimeFormatter.ofPattern(pattern)))
            Text(text = workoutInfo)
        }
    }

}

@Preview
@Composable
fun preWork() {
    WorkoutComponent(
        Icons.Default.SportsGymnastics,
        "Sport", LocalDateTime.now(),
        "20 km walking")
}