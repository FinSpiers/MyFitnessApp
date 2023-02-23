package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.getWorkoutInfo

@Composable
fun RecentWorkouts(onClick: () -> Unit) {

    val sportActivities: List<SportActivity> = listOf(
        SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        ), SportActivity.PushUp(
            5.0, 20, 400
        ), SportActivity.BicycleRiding(
            11.0, 20.2, 1.0, 14.0, 1000
        ), SportActivity.Squat(
            20.0, 30, 3
        ), SportActivity.WalkingHiking(
            5.0, 1.0, 5.0, 5.0, 100
        ), SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        )
    )

    Column {
        sportActivities.forEach {
            WorkoutComponent(getWorkoutInfo(it), onClick)
        }
    }
}