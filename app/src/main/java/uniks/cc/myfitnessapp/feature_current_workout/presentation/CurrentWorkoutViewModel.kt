package uniks.cc.myfitnessapp.feature_current_workout.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import javax.inject.Inject


@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository
) : ViewModel() {
    val currentWorkout: Workout = workoutRepository.currentWorkout
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")

    fun onNavigationAction(navigationEvent: NavigationEvent) {
        coreRepository.onNavigationAction(navigationEvent)
    }

    fun onWorkoutAction(workoutEvent: WorkoutEvent) {
        workoutRepository.onWorkoutAction(workoutEvent)
    }

    val accelerometerSensor = sensorRepository.getAccelerometerSensor()
    val accelerometerSensorValueX = mutableStateOf(0.0)
    val accelerometerSensorValueY = mutableStateOf(0.0)
    val accelerometerSensorValueZ = mutableStateOf(0.0)

    val stepCounterSensor = sensorRepository.getStepCounterSensor()
    val stepCounterSensorValue = mutableStateOf(0)

    init {
        // TODO: same code as in the sensor repos, should be changed
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            accelerometerSensorValueX.value = values[0].toDouble()
            accelerometerSensorValueY.value = values[1].toDouble()
            accelerometerSensorValueZ.value = values[2].toDouble()
        }

        if (!accelerometerSensor.isListening()) {
            accelerometerSensor.startListening()
        }

        stepCounterSensor.setOnSensorValuesChangedListener { values ->
            stepCounterSensorValue.value = values[0].toInt()
        }

        if (!stepCounterSensor.isListening()) {
            stepCounterSensor.startListening()
        }
    }
}