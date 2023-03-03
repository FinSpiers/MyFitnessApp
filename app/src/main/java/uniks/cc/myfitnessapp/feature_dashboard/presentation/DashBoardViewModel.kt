package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_current_workout.data.data_source.StepCounterWorker
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,

    ) : ViewModel() {
    val dashBoardState = mutableStateOf(DashBoardState())

    init {
        workoutRepository.onWorkoutAction = this::onWorkoutAction
        syncRepoWorkouts()
        val workManager : WorkManager = WorkManager.getInstance(coreRepository.context)
        val workRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<StepCounterWorker>(15, TimeUnit.MINUTES).build()

        workManager.enqueueUniquePeriodicWork("stepCounterWork", ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    fun getCurrentWorkout() : Workout? {
        return workoutRepository.currentWorkout
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

                dashBoardState.value = dashBoardState.value.copy(
                    workouts = dashBoardState.value.workouts.toMutableList().apply { add(0, currentWorkout) }
                )

                workoutRepository.workouts = dashBoardState.value.workouts

            }
            is WorkoutEvent.StopWorkout -> {
                workoutRepository.currentWorkout = null
                /* TODO: Stop Service(s) */
            }
        }
    }

    private fun syncRepoWorkouts() {
        val workouts = getAllWorkoutsFromDatabase()
        workoutRepository.workouts = workouts
        dashBoardState.value = dashBoardState.value.copy(
            workouts = workouts
        )
    }

    fun getAllWorkouts() : List<Workout> {
        syncRepoWorkouts()
        return workoutRepository.workouts
    }

    @SuppressLint("MissingPermission")
    fun hasGpsSignal() : Boolean {
        return locationManager.isLocationEnabled
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
                    dashBoardState.value = dashBoardState.value.copy(
                        currentWeatherData = coreRepository.getCurrentWeather(location.latitude, location.longitude)
                    )
                }
            }
        }
    }
}