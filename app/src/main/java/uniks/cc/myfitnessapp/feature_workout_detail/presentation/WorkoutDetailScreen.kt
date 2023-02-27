package uniks.cc.myfitnessapp.feature_workout_detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter

@Composable
fun WorkoutDetailScreen(
    durationUnit: String = "h",
    distanceUnit: String = "km",
    paceUnit: String = "km/h",
    kcalUnit: String = "kcal",
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = viewModel.selectedWorkout.imageId),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                contentDescription = viewModel.selectedWorkout.workoutName,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
            Text(
                text = viewModel.selectedWorkout.workoutName,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(4.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = TimestampConverter.convertToDate(viewModel.selectedWorkout.timeStamp),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = "Duration",
                textAlign = TextAlign.Left
            )
            Text(
                text = "${viewModel.selectedWorkout.duration} $durationUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
        if (viewModel.selectedWorkout.distance != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = "Distance",
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${viewModel.selectedWorkout.distance} $distanceUnit",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
        if (viewModel.selectedWorkout.avgPace != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = "avg Pace",
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${viewModel.selectedWorkout.avgPace} $paceUnit",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
        if (viewModel.selectedWorkout.repetitions != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = "Repetitions",
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "${viewModel.selectedWorkout.repetitions}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = "kcal",
                textAlign = TextAlign.Left
            )
            Text(
                text = "${viewModel.selectedWorkout.kcal} $kcalUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
    }
}