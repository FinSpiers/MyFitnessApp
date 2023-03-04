package uniks.cc.myfitnessapp.core.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import uniks.cc.myfitnessapp.core.domain.model.sensors.AccelerometerSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor

interface SensorRepository {

    var stepCounterSensorValueStateFlow: MutableStateFlow<Int>
    var accelerometerSensorValueX: Double
    var accelerometerSensorValueY: Double
    var accelerometerSensorValueZ: Double
    fun getStepCounterSensor(): StepCounterSensor

    fun getAccelerometerSensor(): AccelerometerSensor

    fun startStepCounterSensor()

    fun stopStepCounterSensor()

    fun startAccelerometerSensor()

    fun stopAccelerometerSensor()

}