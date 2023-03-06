package uniks.cc.myfitnessapp.core.data.repository

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.model.sensors.AccelerometerSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository

class SensorRepositoryImpl(
    private val stepCounterSensor: StepCounterSensor,
    private val accelerometerSensor: AccelerometerSensor
) : SensorRepository {

    override var stepCounterSensorValueStateFlow = MutableStateFlow(0)
    override var accelerometerSensorValueX: Double = 0.0
    override var accelerometerSensorValueY: Double = 0.0
    override var accelerometerSensorValueZ: Double = 0.0
    override fun getStepCounterSensor(): StepCounterSensor {
        return stepCounterSensor
    }

    override fun getAccelerometerSensor(): AccelerometerSensor {
        return accelerometerSensor
    }

    override fun startAccelerometerSensor() {
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            accelerometerSensorValueX = values[0].toDouble()
            accelerometerSensorValueY = values[1].toDouble()
            accelerometerSensorValueZ = values[2].toDouble()

        }

        if (!accelerometerSensor.isListening()) {
            accelerometerSensor.startListening()
        }
    }

    override fun stopAccelerometerSensor() {
        accelerometerSensor.stopListening()
    }

    override fun startStepCounterSensor() {
        stepCounterSensor.setOnSensorValuesChangedListener { values ->
            stepCounterSensorValueStateFlow.tryEmit(values[0].toInt())

        }

        if (!stepCounterSensor.isListening()) {
            stepCounterSensor.startListening()
        }
    }

    override fun stopStepCounterSensor() {
        stepCounterSensor.stopListening()
    }

}