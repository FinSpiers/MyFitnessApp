package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.getWorkoutInfo

@Composable
fun RecentWorkouts() {

    val sportActivities: List<SportActivity> = listOf(
        SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        ), SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        ), SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        ), SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        ), SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        ), SportActivity.Running(
            20.0, 20.0, 4.0, 4.0, 1000
        )
    )

    Column {
        sportActivities.forEach {
            WorkoutComponent(getWorkoutInfo(it))
        }
    }
}

@Preview
@Composable
fun testWorkout() {
    RecentWorkouts()
}