package uniks.cc.myfitnessapp.feature_workout.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import javax.inject.Inject

@HiltViewModel
class WorkoutTypeBViewModel @Inject constructor(
    private val sensorRepository: SensorRepository
) : ViewModel() {

    val gyroscopeSensorValueX = sensorRepository.gyroscopeSensorValueX.value
    val stepSensorValue = sensorRepository.stepCounterSensorValue.value

    init {

        sensorRepository.startGyroscopeSensor()
        sensorRepository.stopStepCounterSensor()

    }
}