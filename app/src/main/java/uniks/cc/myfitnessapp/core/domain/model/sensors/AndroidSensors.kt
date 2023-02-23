package uniks.cc.myfitnessapp.core.domain.model.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class AndroidSensors(
    private val context: Context,
    private val sensorFeature: String,
    val sensorType: Int
) : SensorEventListener {

    private val sensorExists: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var isListening = false
    private var onSensorValuesChanged: ((List<Float>) -> Unit)? = null


    fun setOnSensorValuesChangedListener(listener: ((List<Float>) -> Unit)) {
        onSensorValuesChanged = listener
    }

    fun isListening(): Boolean {
        return isListening
    }

    fun startListening() {
        if (!sensorExists) {
            return
        }
        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            isListening = true
        }
    }

    fun stopListening() {
        if (!sensorExists || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
        isListening = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!sensorExists) {
            return
        }
        if (event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}