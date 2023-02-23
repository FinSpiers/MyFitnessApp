package uniks.cc.myfitnessapp.core.domain.repository

import androidx.compose.runtime.MutableState
import uniks.cc.myfitnessapp.core.domain.model.sensors.AndroidSensors
import uniks.cc.myfitnessapp.core.domain.model.sensors.GyroscopeSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor

interface SensorRepository {

    val stepCounterSensorValue: MutableState<Int>
    val gyroscopeSensorValueX: MutableState<Double>
    val gyroscopeSensorValueY: MutableState<Double>
    val gyroscopeSensorValueZ: MutableState<Double>

    fun getStepCounterSensor(): AndroidSensors

    fun getGyroscopeSensor(): AndroidSensors

    fun startStepCounterSensor()

    fun stopStepCounterSensor()

    fun startGyroscopeSensor()

    fun stopGyroscopeSensor()

}