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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_current_workout.data.calculator.DistanceCalculator
import uniks.cc.myfitnessapp.feature_current_workout.data.worker.CardioWorkoutWorker
import uniks.cc.myfitnessapp.feature_current_workout.data.worker.StepCounterResetWorker
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val workManager: WorkManager,
    private val stopwatchManager: StopwatchManager

) : ViewModel() {
    val dashBoardState = mutableStateOf(DashBoardState())
    val stepCounterStateFlow = sensorRepository.stepCounterSensorValueStateFlow
    val dialogStateFlow = MutableStateFlow(false)


    init {
        sensorRepository.startStepCounterSensor()
        workoutRepository.onWorkoutAction = this::onWorkoutAction


        val initialDelay = Duration.between(
            LocalDateTime.now(),
            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0))
        )

        val resetRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<StepCounterResetWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(initialDelay)
                .build()

        workManager.enqueueUniquePeriodicWork(
            "stepCounterResetWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            resetRequest
        )

        val exampleRoute = listOf(
            Waypoint(1, 1, 51.546109235121925, 9.401057125476921),
            Waypoint(1, 12, 51.54551062095242, 9.401252428123303),
            Waypoint(1, 123, 51.544721964433144, 9.402062357757353),
            Waypoint(1, 1234, 51.54473609118497, 9.403702405617599),
            Waypoint(1, 12345, 51.54547350154158, 9.40395227440517),
            Waypoint(1, 123456, 51.546109235121925, 9.401057125476921),
        )

        val calculate = DistanceCalculator.calculateMeters(exampleRoute)
        Log.e("DISTANCE", calculate.toString())

    }

    fun getOldStepCount() : Int {
        var oldStepsValue = emptyList<Steps>()
        runBlocking {
            oldStepsValue = workoutRepository.getAllDailySteps()
        }
        return oldStepsValue.sumOf { it.count }
    }

    fun getCurrentWorkout(): Workout? {
        return workoutRepository.currentWorkout
    }

    fun hasCurrentWorkout(): Boolean {
        return workoutRepository.currentWorkout != null
    }


    fun getAllWorkoutsFromDatabase(): List<Workout> {
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

    fun onWorkoutAction(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.StartWorkout -> {
                var currentWorkout = Workout(
                    workoutName = event.workoutName,
                    timeStamp = Instant.now().epochSecond,
                    duration = 0.0,
                    kcal = 0
                ).apply {
                    if (workoutName in listOf("Walking", "Running", "Bicycling")) {
                        distance = 0.0
                        pace = 0.0
                        avgPace = 0.0
                        // TODO: use activity recognition api and a backgroundService to
                        // TODO: track users activities,time spend, calculate distance, pace, burned kcal, etc..
                        val cardioWorkoutWorker = OneTimeWorkRequestBuilder<CardioWorkoutWorker>().build()
                        workManager.enqueue(cardioWorkoutWorker)
                    }
                    else {
                        repetitions = 0
                        // TODO: Use backgroundService that tracks the time and repetitions for the activity
                    }
                }
                runBlocking {
                    workoutRepository.addWorkoutToDatabase(currentWorkout)
                    currentWorkout = workoutRepository.getWorkoutByTimestamp(currentWorkout.timeStamp)!!
                }
                workoutRepository.currentWorkout = currentWorkout

                dashBoardState.value = dashBoardState.value.copy(
                    workouts = dashBoardState.value.workouts.toMutableList()
                        .apply { add(0, currentWorkout) }
                )

                workoutRepository.workouts = dashBoardState.value.workouts

            }
            is WorkoutEvent.StopWorkout -> {
                workoutRepository.currentWorkout = null
                stopwatchManager.stop()
            }
        }
    }

    fun syncRepoWorkouts() {
        val workouts = getAllWorkoutsFromDatabase()
        workoutRepository.workouts = workouts
        dashBoardState.value = dashBoardState.value.copy(
            workouts = workouts
        )
    }

    fun getAllWorkouts(): List<Workout> {
        syncRepoWorkouts()
        return workoutRepository.workouts
    }

    @SuppressLint("MissingPermission")
    fun hasGpsSignal(): Boolean {
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
                        currentWeatherData = coreRepository.getCurrentWeather(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }
        }
    }
}