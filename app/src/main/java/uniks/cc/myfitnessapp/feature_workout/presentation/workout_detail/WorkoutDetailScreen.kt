package uniks.cc.myfitnessapp.feature_workout.presentation.workout_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_BICYCLING
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_RUNNING
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_WALKING
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.core.presentation.components.WorkoutFab
import uniks.cc.myfitnessapp.feature_workout.presentation.workout_detail.components.LineChartBox
import uniks.cc.myfitnessapp.feature_workout.presentation.workout_detail.components.MapBox
import uniks.cc.myfitnessapp.feature_workout.presentation.workout_detail.components.WorkoutDetailListItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkoutDetailScreen(
    durationUnit: String = "",
    distanceUnit: String = "m",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                WorkoutDetailListItem(
                    title = "Duration",
                    value = viewModel.selectedWorkout.duration,
                    unit = durationUnit
                )

                if (viewModel.selectedWorkout.workoutName in listOf(
                        WORKOUT_WALKING,
                        WORKOUT_RUNNING,
                        WORKOUT_BICYCLING
                    )
                ) {
                    WorkoutDetailListItem(
                        title = "Distance",
                        value = "${viewModel.selectedWorkout.distance}",
                        unit = distanceUnit
                    )
                    WorkoutDetailListItem(
                        title = "avg Pace",
                        value = "${viewModel.selectedWorkout.avgPace}",
                        unit = paceUnit
                    )


                } else {
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
                Spacer(modifier = Modifier.height(20.dp))

                if (viewModel.selectedWorkout.workoutName in listOf(
                        WORKOUT_WALKING,
                        WORKOUT_RUNNING,
                        WORKOUT_BICYCLING
                    )
                ) {
                    MapBox(
                        true,
                        viewModel.getWaypointsByWorkoutId(viewModel.selectedWorkout.id)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    val timestamps = arrayListOf<String>()
                    val paceValues = arrayListOf<Float>()
                    val altitudeValues = arrayListOf<Float>()

                    for (waypoint in viewModel.getWaypointsByWorkoutId(viewModel.selectedWorkout.id)) {
                        timestamps.add(TimestampConverter.convertToTime(waypoint.timeStamp))
                        paceValues.add(waypoint.currentPace.toFloat())
                        altitudeValues.add(waypoint.altitude.toFloat())
                    }

                    LineChartBox(
                        "Pace",
                        timestamps,
                        paceValues
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    LineChartBox(
                        "Altitude",
                        timestamps,
                        altitudeValues
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                }

            }
        }
    }
}
