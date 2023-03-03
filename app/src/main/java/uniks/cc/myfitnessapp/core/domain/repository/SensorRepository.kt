package uniks.cc.myfitnessapp.core.domain.repository

import androidx.compose.runtime.MutableState
import uniks.cc.myfitnessapp.core.domain.model.sensors.AndroidSensors

interface SensorRepository {

    val stepCounterSensorValue: MutableState<Int>
    val accelerometerSensorValueX: MutableState<Double>
    val accelerometerSensorValueY: MutableState<Double>
    val accelerometerSensorValueZ: MutableState<Double>

    fun getStepCounterSensor(): AndroidSensors

    fun getAccelerometerSensor(): AndroidSensors

    fun startStepCounterSensor()

    fun stopStepCounterSensor()

    fun startAccelerometerSensor()

    fun stopAccelerometerSensor()

}