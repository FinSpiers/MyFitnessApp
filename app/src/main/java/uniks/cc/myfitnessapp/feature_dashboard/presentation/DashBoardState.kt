package uniks.cc.myfitnessapp.feature_dashboard.presentation

import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData

data class DashBoardState(
    var currentWeatherData : CurrentWeatherData = CurrentWeatherData(),
    var workouts : List<Workout> = mutableListOf(),
    var selectedWorkoutDetail : Workout? = null,
    var currentWorkout : Workout? = null,
    var steps : Int = 0
)
