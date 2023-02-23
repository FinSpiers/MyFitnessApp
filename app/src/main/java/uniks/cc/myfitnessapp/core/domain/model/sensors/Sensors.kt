package uniks.cc.myfitnessapp.core.domain.model.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class StepCounterSensor(
    context: Context
) : AndroidSensors(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_STEP_COUNTER,
    sensorType = Sensor.TYPE_STEP_COUNTER
)


class GyroscopeSensor(
    context: Context
) : AndroidSensors(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE
)