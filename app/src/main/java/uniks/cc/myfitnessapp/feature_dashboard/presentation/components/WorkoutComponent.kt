package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import kotlin.reflect.KFunction1

@Composable
fun WorkoutComponent(
    model: Workout,
    onClick: KFunction1<Workout, Unit>,
    isCurrentWorkout: Boolean = false
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(1.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick(model) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.45f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = model.imageId),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                contentDescription = model.workoutName,
                modifier = Modifier
                    .size(75.dp)
                    .padding(8.dp)
            )
            Text(
                text = model.workoutName,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            if (isCurrentWorkout) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color.Green, CircleShape)
                    )
                    Text(
                        text = "active",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = TimestampConverter.convertToDate(model.timeStamp),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = TimestampConverter.convertToTime(model.timeStamp),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

    }
}


