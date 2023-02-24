package uniks.cc.myfitnessapp.core.presentation

sealed class WorkoutEvent {
    class StartWorkout(val workoutName : String) : WorkoutEvent()

    object StopWorkout : WorkoutEvent()
}
