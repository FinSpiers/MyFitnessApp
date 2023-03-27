package uniks.cc.myfitnessapp.feature_workout.presentation.current_workout

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.domain.util.Constants
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository,
    private val stopwatchManager: StopwatchManager
) : ViewModel() {
    var currentWorkout : Workout

    val currentWorkoutDistanceStateFlow = workoutRepository.currentWorkoutDistanceStateFlow
    var timerFlow : MutableStateFlow<String>

    val hasError = workoutRepository.hasError
    var errorTitle = workoutRepository.errorTitle
    var errorText = workoutRepository.errorText

    init {
        runBlocking {
            currentWorkout = workoutRepository.currentWorkout!!
            workoutRepository.onError = this@CurrentWorkoutViewModel::OnError
        }
        timerFlow = stopwatchManager.ticker as MutableStateFlow<String>

        if (currentWorkout.workoutName in listOf(
                Constants.WORKOUT_WALKING,
                Constants.WORKOUT_RUNNING,
                Constants.WORKOUT_BICYCLING
            )
        ) {
            //TODO: TypeAWorkout
        } else {
            //TODO: TypeBWorkout
            sensorRepository.startAccelerometerSensor()
        }
    }

    fun OnError(errorTitle : String, errorText : String) {
        workoutRepository.errorTitle.value = errorTitle
        workoutRepository.errorText.value = errorText
        workoutRepository.hasError.value = true
    }

    fun onNavigationAction(navigationEvent: NavigationEvent) {
        coreRepository.onNavigationAction(navigationEvent)
    }

    fun onWorkoutAction(workoutEvent: WorkoutEvent) {
        workoutRepository.onWorkoutAction(workoutEvent)
    }

    override fun onCleared() {
        super.onCleared()
        sensorRepository.stopAccelerometerSensor()
    }
}