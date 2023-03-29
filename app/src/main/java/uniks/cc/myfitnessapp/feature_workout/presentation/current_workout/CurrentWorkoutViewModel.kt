package uniks.cc.myfitnessapp.feature_workout.presentation.current_workout


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository,
    stopwatchManager: StopwatchManager
) : ViewModel() {
    var currentWorkout: Workout

    val currentWorkoutDistanceStateFlow = workoutRepository.currentWorkoutDistanceStateFlow
    val currentWorkoutPaceStateFlow = workoutRepository.currentWorkoutPaceStateFlow
    var timerFlow: MutableStateFlow<String>

    val hasError = workoutRepository.hasError
    var errorTitle = workoutRepository.errorTitle
    var errorText = workoutRepository.errorText

    init {
        runBlocking {
            currentWorkout = workoutRepository.currentWorkout!!
        }

        timerFlow = stopwatchManager.ticker as MutableStateFlow<String>
    }

    fun incrementRepetitions() {
        if (currentWorkout.repetitions == null) {
            return
        }
        currentWorkout.repetitions = currentWorkout.repetitions!! + 1
        currentWorkout.duration = timerFlow.value
        viewModelScope.launch {
            workoutRepository.addWorkoutToDatabase(currentWorkout)
        }
    }

    fun onNavigationAction(navigationEvent: NavigationEvent) {
        coreRepository.onNavigationAction(navigationEvent)
    }

    fun onWorkoutAction(workoutEvent: WorkoutEvent) {
        if (workoutEvent is WorkoutEvent.StopWorkout) {
            currentWorkout.duration = timerFlow.value
            viewModelScope.launch {
                workoutRepository.addWorkoutToDatabase(currentWorkout)
            }
        }
        workoutRepository.onWorkoutAction(workoutEvent)
    }

    override fun onCleared() {
        super.onCleared()
        sensorRepository.stopAccelerometerSensor()
    }
}