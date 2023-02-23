package uniks.cc.myfitnessapp.core.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import uniks.cc.myfitnessapp.core.domain.model.sensors.AndroidSensors
import uniks.cc.myfitnessapp.core.domain.model.sensors.GyroscopeSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository

class SensorRepositoryImpl(
    private val context: Context
) : SensorRepository {


    override val stepCounterSensorValue = mutableStateOf(0)
    override val gyroscopeSensorValueX = mutableStateOf(0.0)
    override val gyroscopeSensorValueY = mutableStateOf(0.0)
    override val gyroscopeSensorValueZ = mutableStateOf(0.0)

    override fun getStepCounterSensor(): AndroidSensors {
        return StepCounterSensor(context = context)
    }

    override fun getGyroscopeSensor(): AndroidSensors {
        return GyroscopeSensor(context = context)
    }

    override fun startGyroscopeSensor() {
        getGyroscopeSensor().setOnSensorValuesChangedListener { values ->
            gyroscopeSensorValueX.value = values[0].toDouble()
            gyroscopeSensorValueY.value = values[1].toDouble()
            gyroscopeSensorValueZ.value = values[2].toDouble()
            println("hier gy " + values[0].toInt())
        }

        if (!getGyroscopeSensor().isListening()) {
            getGyroscopeSensor().startListening()
        }
    }

    override fun stopGyroscopeSensor() {
        getGyroscopeSensor().stopListening()
    }

    override fun startStepCounterSensor() {
        getStepCounterSensor().setOnSensorValuesChangedListener { values ->
            stepCounterSensorValue.value = values[0].toInt()
            println("hier steps " + values[0].toInt())
        }

        if (!getStepCounterSensor().isListening()) {
            getStepCounterSensor().startListening()
        }
    }

    override fun stopStepCounterSensor() {
        getStepCounterSensor().stopListening()
    }

}