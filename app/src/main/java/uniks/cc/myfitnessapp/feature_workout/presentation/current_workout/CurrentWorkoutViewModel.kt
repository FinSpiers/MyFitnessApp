package uniks.cc.myfitnessapp.feature_workout.presentation.current_workout

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
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
    val stopwatchManager: StopwatchManager
) : ViewModel() {
    val currentWorkout: Workout = workoutRepository.currentWorkout
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")

    val currentWorkoutDistanceStateFlow = workoutRepository.currentWorkoutDistanceStateFlow

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