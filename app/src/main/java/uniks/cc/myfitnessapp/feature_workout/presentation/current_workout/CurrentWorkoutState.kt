package uniks.cc.myfitnessapp.feature_workout.presentation.current_workout

import uniks.cc.myfitnessapp.core.domain.model.Workout

data class CurrentWorkoutState(
    val workout: Workout? = null,
    val hasCurrentWorkout: Boolean = (workout != null),
    val duration: String = "00:00:000",
    val distance: Double = 0.0,
    val pace: Double = 0.0
)
