package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.util.Screen
import uniks.cc.myfitnessapp.core.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import kotlin.reflect.KFunction1

const val WORKOUT_WALKING = "Walking"
const val WORKOUT_RUNNING = "Running"
const val WORKOUT_BICYCLING = "Bicycling"
const val WORKOUT_PUSHUPS = "PushUps"
const val WORKOUT_SITUPS = "SitUps"
const val WORKOUT_SQUATS = "Squats"

@Composable
fun WorkoutFab(
    navbarState: NavigationBarState,
    onWorkoutNavigation: KFunction1<NavigationEvent, Unit>,
    onWorkoutAction: KFunction1<WorkoutEvent, Unit>

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
                    WORKOUT_WALKING, R.drawable.image_walking, onWorkoutNavigation, onWorkoutAction, isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_RUNNING, R.drawable.image_jogging, onWorkoutNavigation, onWorkoutAction, isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_BICYCLING, R.drawable.image_bicycling, onWorkoutNavigation, onWorkoutAction, isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_PUSHUPS, R.drawable.image_push_ups, onWorkoutNavigation, onWorkoutAction, isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_SITUPS, R.drawable.image_sit_ups, onWorkoutNavigation, onWorkoutAction, isButtonExtended
                )
                StartWorkoutListItem(
                    WORKOUT_SQUATS, R.drawable.image_squats, onWorkoutNavigation, onWorkoutAction, isButtonExtended
                )
            }
        }
        var text: String = ""
        var icon: ImageVector = Icons.Filled.Add
        var onClick : () -> Unit = {}
        var showButton : Boolean = false

        if (isButtonExtended.value) {
            text = "Choose Workout"
            icon = Icons.Filled.ArrowDropDown
            onClick = { isButtonExtended.value = ! isButtonExtended.value }
            showButton = true
        }
        else if (navbarState.currentRoute == "dashboard" && navbarState.subRoute != Screen.CurrentActivityScreen.route) {
            text = "Start Workout"
            icon = Icons.Filled.Add
            onClick = { isButtonExtended.value = ! isButtonExtended.value }
            showButton = true

        } else if (navbarState.currentRoute == "dashboard" && navbarState.subRoute == Screen.CurrentActivityScreen.route) {
            text = "Stop Workout"
            icon = Icons.Filled.Stop
            onClick = {
                onWorkoutAction(WorkoutEvent.StopWorkout)
                onWorkoutNavigation(NavigationEvent.OnStopWorkoutClick)
            }
            showButton = true
        }
        else {
            showButton = false
        }
        if (showButton) {
            ExtendedFloatingActionButton(
                text = { Text(text = text) },
                icon = { Icon(icon, text) },
                onClick = onClick
            )
        }
    }
}

@Composable
fun StartWorkoutListItem(
    workoutName: String,
    imageId: Int,
    onWorkoutNavigation: KFunction1<NavigationEvent, Unit>,
    onWorkoutAction: KFunction1<WorkoutEvent, Unit>,
    openDialog : MutableState<Boolean>
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onWorkoutNavigation(NavigationEvent.OnStartWorkoutClick(workoutName))
                onWorkoutAction(WorkoutEvent.StartWorkout(workoutName))
                openDialog.value = false
            }) {
        Icon(
            painter = painterResource(id = imageId),
            contentDescription = workoutName,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                .padding(end = 4.dp)

        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = workoutName, style = MaterialTheme.typography.titleMedium
        )
    }
}