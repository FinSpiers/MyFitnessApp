package uniks.cc.myfitnessapp.feature_workout.presentation

sealed class WorkoutEvent {
    class StartWorkout(val workoutName: String) : WorkoutEvent()

    object StopWorkout : WorkoutEvent()

}
