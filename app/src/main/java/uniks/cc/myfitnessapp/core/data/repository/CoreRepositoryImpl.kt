package uniks.cc.myfitnessapp.core.data.repository

import uniks.cc.myfitnessapp.core.data.database.WorkoutDao
import uniks.cc.myfitnessapp.core.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.core.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.reflect.KFunction1

class CoreRepositoryImpl(
    private val weatherApiService: OpenWeatherApiService,
    private val workoutDao: WorkoutDao
) : CoreRepository {
    override var isLocationPermissionGranted: Boolean = false
    override lateinit var onNavigationAction: KFunction1<NavigationEvent, Unit>
    override lateinit var onWorkoutAction: KFunction1<WorkoutEvent, Unit>
    override var workouts: MutableList<Workout> = mutableListOf()

    override var currentWorkout: Workout? = null
    override var selectedWorkoutDetail: Workout? = null

    override fun setLocationPermissionGranted() {
        isLocationPermissionGranted = true
    }

    override suspend fun getAllWorkoutsFromDatabase(): List<Workout> {
        return workoutDao.getAllWorkouts()
    }

    override suspend fun addWorkoutToDatabase(workout: Workout) {
        workoutDao.addWorkout(workout)
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): CurrentWeatherData {
        return weatherApiService.getCurrentWeatherAsync(lat, lon, "metric", "en").await()
            .toCurrentWeatherData()
    }

    override fun hasCurrentWorkout(): Boolean {
        return currentWorkout != null
    }
}
