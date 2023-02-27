package uniks.cc.myfitnessapp.feature_active_workout.presentation

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
import uniks.cc.myfitnessapp.feature_active_workout.domain.util.WorkoutMap
import uniks.cc.myfitnessapp.feature_core.presentation.components.DataBox

@Composable
fun CurrentWorkoutScreen(viewModel: CurrentWorkoutViewModel = hiltViewModel()) {
    val currentWorkout = viewModel.currentWorkout
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
                .padding(3.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageId = WorkoutMap.map[currentWorkout.workoutName]
            if (imageId != null) {
                Image(
                    painterResource(id = imageId),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                    contentDescription = currentWorkout.workoutName,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            Text(
                text = currentWorkout.workoutName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (currentWorkout.distance != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DataBox(
                        title = "Distance",
                        data = (currentWorkout.distance).toString(),
                        unit = "km"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DataBox(
                    title = "Duration",
                    data = (currentWorkout.duration).toString(),
                    unit = "min"
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (currentWorkout.repetitions != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DataBox(
                        title = "Repetitions",
                        data = (currentWorkout.repetitions).toString(),
                        unit = ""
                    )
                }
            }
            if (currentWorkout.pace != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DataBox(
                        title = "Pace",
                        data = (currentWorkout.pace).toString(),
                        unit = "km/h"
                    )
                }
            }
        }
    }
}