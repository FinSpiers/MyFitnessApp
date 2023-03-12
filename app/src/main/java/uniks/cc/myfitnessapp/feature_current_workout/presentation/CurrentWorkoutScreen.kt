@file:OptIn(ExperimentalMaterial3Api::class)

package uniks.cc.myfitnessapp.feature_current_workout.presentation

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.core.presentation.components.WorkoutFab
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.WorkoutMap
import uniks.cc.myfitnessapp.feature_core.presentation.components.DataBox

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentWorkoutScreen(viewModel: CurrentWorkoutViewModel = hiltViewModel()) {
    val currentWorkout = viewModel.currentWorkout
    Scaffold(
        floatingActionButton = {
            WorkoutFab(
                onNavigationAction = viewModel::onNavigationAction,
                onWorkoutAction = viewModel::onWorkoutAction,
                hasCurrentWorkout = true
            )
        }
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
                val imageId = WorkoutMap.map[currentWorkout.workoutName]
                if (imageId != null) {
                    Image(
                        painterResource(id = imageId),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                        contentDescription = currentWorkout.workoutName,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(8.dp)
                    )
                }
                Text(
                    text = currentWorkout.workoutName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (currentWorkout.distance != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DataBox(
                            title = "Distance",
                            data = viewModel.currentWorkoutDistanceStateFlow.collectAsState().value,
                            unit = "km"
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DataBox(
                        title = "Duration",
                        data = (viewModel.stopwatchManager.ticker.collectAsState().value),
                        unit = ""
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (currentWorkout.repetitions != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
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
                            .padding(4.dp),
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
}