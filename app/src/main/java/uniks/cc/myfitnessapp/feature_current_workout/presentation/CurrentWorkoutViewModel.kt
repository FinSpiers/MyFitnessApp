package uniks.cc.myfitnessapp.feature_current_workout.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch.StopwatchOrchestrator
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository,
    val stopwatchOrchestrator: StopwatchOrchestrator
) : ViewModel() {
    val currentWorkout: Workout = workoutRepository.currentWorkout
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")

    val currentWorkoutTimerStateFlow = workoutRepository.currentWorkoutTimerStateFlow

    fun onNavigationAction(navigationEvent: NavigationEvent) {
        coreRepository.onNavigationAction(navigationEvent)
    }

    fun onWorkoutAction(workoutEvent: WorkoutEvent) {
        workoutRepository.onWorkoutAction(workoutEvent)
    }

    init {
        if (currentWorkout.workoutName in listOf(
                "Walking",
                "Running",
                "Bicycling"
            )
        ) {
            //TODO: TypeAWorkout
        } else {
            //TODO: TypeBWorkout
            sensorRepository.startAccelerometerSensor()
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorRepository.stopAccelerometerSensor()
    }
}