package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.presentation.DashBoardState
import kotlin.reflect.*


@Composable
fun RecentWorkouts(dashBoardState: DashBoardState, onClick: KFunction1<Workout, Unit>) {
    /* TODO: Delete if statement after testing */
    Column {
        dashBoardState.workouts.forEach {
            if (it.workoutName=="Walking") {
                WorkoutComponent(model = it, onClick = onClick, isCurrentWorkout = it.id == dashBoardState.currentWorkout?.id)
            }
            else {
                WorkoutComponent(it, onClick)
            }
        }
    }
}
