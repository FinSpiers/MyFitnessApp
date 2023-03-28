package uniks.cc.myfitnessapp.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import kotlin.reflect.KFunction1

@Composable
fun StartWorkoutListItem(
    workoutName: String,
    imageId: Int,
    onWorkoutNavigation: KFunction1<NavigationEvent, Unit>,
    onWorkoutAction: KFunction1<WorkoutEvent, Unit>,
    openDialog: MutableState<Boolean>
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