package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.core.domain.model.Workout
import kotlin.reflect.*


@Composable
fun RecentWorkouts(
    workouts: List<Workout>,
    onClick: KFunction1<Workout, Unit>,
    currentWorkout: Workout?
) {
    if (workouts.isNotEmpty()) {
        LazyColumn {
            workouts.forEach {
                item {
                    WorkoutComponent(
                        model = it,
                        onClick = onClick,
                        isCurrentWorkout = it.id == currentWorkout?.id
                    )
                }
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 32.dp, start = 8.dp, end = 8.dp)
        ) {

            Text(
                text = "No workout yet!",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "You can start a workout manually by clicking on the \n'Start Workout' Button.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
