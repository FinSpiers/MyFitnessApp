package uniks.cc.myfitnessapp.core.domain.repository

import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.reflect.KFunction1

interface CoreRepository {
    var isLocationPermissionGranted : Boolean
    var onNavigationAction : KFunction1<NavigationEvent, Unit>
    var onWorkoutAction : KFunction1<WorkoutEvent, Unit>
    var currentWorkout: Workout?
    var selectedWorkoutDetail : Workout?
    fun setLocationPermissionGranted()

    suspend fun getAllWorkoutsFromDatabase() : List<Workout>

    suspend fun addWorkoutToDatabase(workout: Workout)

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): CurrentWeatherData

    fun hasCurrentWorkout() : Boolean

}