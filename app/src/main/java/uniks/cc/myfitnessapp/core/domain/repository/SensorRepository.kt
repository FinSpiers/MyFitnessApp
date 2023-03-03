package uniks.cc.myfitnessapp.core.domain.repository

import uniks.cc.myfitnessapp.core.domain.model.sensors.AccelerometerSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor

interface SensorRepository {

    var stepCounterSensorValue: Int
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