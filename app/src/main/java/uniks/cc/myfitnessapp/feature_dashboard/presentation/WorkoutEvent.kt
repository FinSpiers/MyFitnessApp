package uniks.cc.myfitnessapp.feature_dashboard.presentation

sealed class WorkoutEvent {
    class StartWorkout(val workoutName : String) : WorkoutEvent()

    object StopWorkout : WorkoutEvent()

    sealed class OnError : WorkoutEvent() {
        object NoLocationPermissionOnError : OnError()
        object NoActivityRecognitionPermissionOnError : OnError()
        object NoNotificationPermissionOnError : OnError()
    }
}
