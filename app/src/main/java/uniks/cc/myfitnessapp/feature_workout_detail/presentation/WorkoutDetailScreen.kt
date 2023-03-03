@file:OptIn(ExperimentalMaterial3Api::class)

package uniks.cc.myfitnessapp.feature_workout_detail.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.sensors.Waypoint
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.core.presentation.components.WorkoutFab
import uniks.cc.myfitnessapp.feature_workout_detail.presentation.components.MapBox
import uniks.cc.myfitnessapp.feature_workout_detail.presentation.components.WorkoutDetailListItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkoutDetailScreen(
    durationUnit: String = "h",
    distanceUnit: String = "km",
    paceUnit: String = "km/h",
    kcalUnit: String = "kcal",
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    Scaffold(floatingActionButton = {
        WorkoutFab(
            onNavigationAction = viewModel::onNavigationAction,
            onWorkoutAction = viewModel::onWorkoutAction,
            hasCurrentWorkout = viewModel.hasCurrentWorkout()
        )
    }) {
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
            WorkoutDetailListItem(
                title = "Duration",
                value = "${viewModel.selectedWorkout.duration}",
                unit = durationUnit
            )
            if (viewModel.selectedWorkout.distance != null) {
                WorkoutDetailListItem(
                    title = "Distance",
                    value = "${viewModel.selectedWorkout.distance}",
                    unit = distanceUnit
                )
            }
            if (viewModel.selectedWorkout.avgPace != null) {
                WorkoutDetailListItem(
                    title = "avg Pace",
                    value = "${viewModel.selectedWorkout.avgPace}",
                    unit = paceUnit
                )
            }
            if (viewModel.selectedWorkout.repetitions != null) {
                WorkoutDetailListItem(
                    title = "Repetitions",
                    value = "${viewModel.selectedWorkout.repetitions}",
                    unit = null
                )
            }
            WorkoutDetailListItem(
                title = "kcal",
                value = "${viewModel.selectedWorkout.kcal}",
                unit = kcalUnit
            )

            val exampleRoute = listOf(
                Waypoint(1, 1, 51.546109235121925, 9.401057125476921),
                Waypoint(1, 12, 51.54551062095242, 9.401252428123303),
                Waypoint(1, 123, 51.544721964433144, 9.402062357757353),
                Waypoint(1, 1234, 51.54473609118497, 9.403702405617599),
                Waypoint(1, 12345, 51.54547350154158, 9.40395227440517),
                Waypoint(1, 123456, 51.546109235121925, 9.401057125476921),
            )

            MapBox(true, exampleRoute)
        }
    }
}