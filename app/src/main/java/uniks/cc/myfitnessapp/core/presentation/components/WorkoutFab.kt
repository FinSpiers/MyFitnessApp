package uniks.cc.myfitnessapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_BICYCLING
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_PUSH_UPS
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_RUNNING
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_SIT_UPS
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_SQUATS
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_WALKING
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import kotlin.reflect.KFunction1


@Composable
fun WorkoutFab(
    onNavigationAction: KFunction1<NavigationEvent, Unit>,
    onWorkoutAction: KFunction1<WorkoutEvent, Unit>,
    hasCurrentWorkout: Boolean

) {
    val isButtonExtended = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small
        )
    ) {
        if (isButtonExtended.value) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(4.dp)
            ) {
                StartWorkoutListItem(
                    WORKOUT_WALKING,
                    R.drawable.image_walking,
                    onNavigationAction,
                    onWorkoutAction,
                    isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_RUNNING,
                    R.drawable.image_jogging,
                    onNavigationAction,
                    onWorkoutAction,
                    isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_BICYCLING,
                    R.drawable.image_bicycling,
                    onNavigationAction,
                    onWorkoutAction,
                    isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_PUSH_UPS,
                    R.drawable.image_push_ups,
                    onNavigationAction,
                    onWorkoutAction,
                    isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_SIT_UPS,
                    R.drawable.image_sit_ups,
                    onNavigationAction,
                    onWorkoutAction,
                    isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_SQUATS,
                    R.drawable.image_squats,
                    onNavigationAction,
                    onWorkoutAction,
                    isButtonExtended
                )
            }
        }
        val text: String
        val icon: ImageVector
        val onClick: () -> Unit

        if (isButtonExtended.value) {
            text = "Choose Workout"
            icon = Icons.Filled.ArrowDropDown
            onClick = { isButtonExtended.value = !isButtonExtended.value }
        } else {
            if (hasCurrentWorkout) {
                text = "Stop Workout"
                icon = Icons.Filled.Stop
                onClick = {
                    onWorkoutAction(WorkoutEvent.StopWorkout)
                    onNavigationAction(NavigationEvent.OnStopWorkoutClick)
                }
            } else {
                text = "Start Workout"
                icon = Icons.Filled.Add
                onClick = { isButtonExtended.value = !isButtonExtended.value }
            }
        }
        ExtendedFloatingActionButton(
            text = { Text(text = text) },
            icon = { Icon(icon, text) },
            onClick = onClick,
            modifier = Modifier.padding(bottom = 70.dp)
        )
    }
}