package uniks.cc.myfitnessapp.feature_dashboard.presentation

import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData

data class DashBoardState(
    var currentWeatherData : CurrentWeatherData = CurrentWeatherData(),
    var workouts : List<Workout> = emptyList(),
    val selectedWorkoutDetail : Workout? = null,
    val currentWorkout : Workout? = null,
    val steps : Int = 0,
    val hasLocationPermission : Boolean = false,
    val hasGpsConnection : Boolean = false,
    val hasInternetConnection : Boolean = false

)
