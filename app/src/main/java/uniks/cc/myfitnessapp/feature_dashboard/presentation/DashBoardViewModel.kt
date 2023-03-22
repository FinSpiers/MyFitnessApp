package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository
import uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker.CardioWorkoutWorker
import uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker.StepCounterResetWorker
import uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker.WeightWorkoutWorker
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val dashBoardRepository: DashBoardRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val workManager: WorkManager

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
    }

    fun getOldStepCount(): Int {
        var oldStepsValue = emptyList<Steps>()
        runBlocking {
            oldStepsValue = dashBoardRepository.getAllDailySteps()
        }
        return oldStepsValue.sumOf { it.dailyCount }
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
                var workerBuilder: OneTimeWorkRequest.Builder
                var currentWorkout = Workout(
                    workoutName = event.workoutName,
                    timeStamp = Instant.now().epochSecond,
                    duration = "00:00:000",
                    kcal = 0
                ).apply {
                    if (workoutName in listOf("Walking", "Running", "Bicycling")) {
                        distance = 0.0
                        pace = 0.0
                        avgPace = 0.0
                        workerBuilder = OneTimeWorkRequestBuilder<CardioWorkoutWorker>()
                    } else {
                        repetitions = 0
                        workerBuilder = OneTimeWorkRequestBuilder<WeightWorkoutWorker>()
                    }
                }
                // Save and load created workout to / from database to ensure id is set
                runBlocking {
                    workoutRepository.addWorkoutToDatabase(currentWorkout)
                    currentWorkout =
                        workoutRepository.getWorkoutByTimestamp(currentWorkout.timeStamp)!!
                }
                workoutRepository.currentWorkout = currentWorkout

                workerBuilder
                    .setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS)
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setInputData(workDataOf("WORKOUT_ID" to currentWorkout.id))

                workManager.enqueueUniqueWork(
                    "currentWorkoutWorker",
                    ExistingWorkPolicy.REPLACE,
                    workerBuilder.build()
                )


                dashBoardState.value = dashBoardState.value.copy(
                    workouts = dashBoardState.value.workouts.toMutableList()
                        .apply { add(0, currentWorkout) }
                )
                workoutRepository.workouts = dashBoardState.value.workouts

            }
            is WorkoutEvent.StopWorkout -> {
                workoutRepository.currentWorkout = null
                viewModelScope.launch(Dispatchers.IO) {
                    //delay(3 * 1000)
                    //workManager.cancelUniqueWork("currentWorkoutWorker")
                }
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
                        currentWeatherData = dashBoardRepository.getCurrentWeatherData(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }
        }
    }
}