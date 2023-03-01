package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,

    ) : ViewModel() {
    private var _dashBoardState: DashBoardState = DashBoardState()
    val dashBoardState = mutableStateOf(_dashBoardState)



    init {
        workoutRepository.onWorkoutAction = this::onWorkoutAction
        syncRepoWorkouts()
    }

    fun getCurrentWorkout() : Workout? {
        return workoutRepository.currentWorkout
    }

    fun addCurrentWorkoutToWorkouts(workout: Workout) {
        val workouts = dashBoardState.value.workouts.toMutableList()
        workouts.add(0, workout)
        _dashBoardState = _dashBoardState.copy(
            workouts = workouts,
            currentWorkout = workout
        )
    }

    fun hasCurrentWorkout() : Boolean {
        return workoutRepository.currentWorkout != null
    }


    private fun getAllWorkoutsFromDatabase(): List<Workout> {
        lateinit var workouts: List<Workout>
        runBlocking { workouts = workoutRepository.getAllWorkoutsFromDatabase() }
        return workouts.sortedByDescending { it.timeStamp }
    }

    fun onWorkoutDetailClick(workout: Workout) {
        workoutRepository.selectedWorkoutDetail = workout
        coreRepository.onNavigationAction(NavigationEvent.OnWorkoutDetailClick(workout))
    }

    fun onNavigationAction(navigationEvent: NavigationEvent) {
        coreRepository.onNavigationAction(navigationEvent)
    }

    fun onWorkoutAction(event : WorkoutEvent) {
        when(event) {
            is WorkoutEvent.StartWorkout -> {
                val currentWorkout = Workout(
                    workoutName = event.workoutName,
                    timeStamp = Instant.now().epochSecond,
                    duration = 0.0,
                    kcal = 0
                ).apply {
                    if(workoutName in listOf("Walking", "Running", "Bicycling")) {
                        distance = 0.0
                        pace = 0.0
                        avgPace = 0.0
                        // TODO: use activity recognition api and a backgroundService to
                        // TODO: track users activities,time spend, calculate distance, pace, burned kcal, etc..
                    }
                    else {
                        repetitions = 0
                        // TODO: Use backgroundService that tracks the time and repetitions for the activity
                    }
                }
                workoutRepository.currentWorkout = currentWorkout
                viewModelScope.launch {
                    workoutRepository.addWorkoutToDatabase(currentWorkout)
                }
                workoutRepository.workouts = _dashBoardState.workouts.toMutableList().apply { add(0, currentWorkout) }

                _dashBoardState.workouts = _dashBoardState.workouts.toMutableList().apply { add(0, currentWorkout) }
            }
            is WorkoutEvent.StopWorkout -> {
                workoutRepository.currentWorkout = null
                /* TODO: Stop Service(s) */
            }
        }
    }

    fun syncRepoWorkouts() {
        workoutRepository.workouts = getAllWorkoutsFromDatabase()
    }

    fun getAllWorkouts() : List<Workout> {
        syncRepoWorkouts()
        return workoutRepository.workouts
    }

    @SuppressLint("MissingPermission")
    fun checkGpsState() {
        fusedLocationProviderClient.locationAvailability.addOnSuccessListener {
            _dashBoardState = _dashBoardState.copy(
                hasGpsConnection = true
            )
        }
        fusedLocationProviderClient.locationAvailability.addOnFailureListener {
            _dashBoardState = _dashBoardState.copy(
                hasGpsConnection = false
            )
        }
    }

    fun hasInternetConnection(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    @SuppressLint("MissingPermission")
    fun setWeatherData() {
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            viewModelScope.launch {
                location?.let {
                    dashBoardState.value.currentWeatherData =
                        coreRepository.getCurrentWeather(location.latitude, location.longitude)
                }
            }
        }
    }
}