package uniks.cc.myfitnessapp.feature_active_workout.presentation

import uniks.cc.myfitnessapp.R

data class CurrentWorkoutState(
    var imageId: Int = R.drawable.image_walking,
    var workoutName: String = "Walking",
    var durationValue: Double = 23.4,
    var distanceValue: Double = 2.1,
    var paceValue: Double = 4.3
)
