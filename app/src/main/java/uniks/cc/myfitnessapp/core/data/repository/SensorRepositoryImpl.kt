package uniks.cc.myfitnessapp.core.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import uniks.cc.myfitnessapp.core.domain.model.sensors.AndroidSensors
import uniks.cc.myfitnessapp.core.domain.model.sensors.AccelerometerSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository

class SensorRepositoryImpl(
    private val context: Context
) : SensorRepository {


    override val stepCounterSensorValue = mutableStateOf(0)
    override val accelerometerSensorValueX = mutableStateOf(0.0)
    override val accelerometerSensorValueY = mutableStateOf(0.0)
    override val accelerometerSensorValueZ = mutableStateOf(0.0)

    override fun getStepCounterSensor(): AndroidSensors {
        return StepCounterSensor(context = context)
    }

    override fun getAccelerometerSensor(): AndroidSensors {
        return AccelerometerSensor(context = context)
    }

    override fun startAccelerometerSensor() {
        getAccelerometerSensor().setOnSensorValuesChangedListener { values ->
            accelerometerSensorValueX.value = values[0].toDouble()
            accelerometerSensorValueY.value = values[1].toDouble()
            accelerometerSensorValueZ.value = values[2].toDouble()
        }

        if (!getAccelerometerSensor().isListening()) {
            getAccelerometerSensor().startListening()
        }
    }

    override fun stopAccelerometerSensor() {
        getAccelerometerSensor().stopListening()
    }

    override fun startStepCounterSensor() {
        getStepCounterSensor().setOnSensorValuesChangedListener { values ->
            stepCounterSensorValue.value = values[0].toInt()
        }

        if (!getStepCounterSensor().isListening()) {
            getStepCounterSensor().startListening()
        }
    }

    override fun stopStepCounterSensor() {
        getStepCounterSensor().stopListening()
    }

}