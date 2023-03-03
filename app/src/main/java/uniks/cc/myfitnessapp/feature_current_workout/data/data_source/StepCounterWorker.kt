package uniks.cc.myfitnessapp.feature_current_workout.data.data_source

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltWorker
class StepCounterWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private var oldValue = 0
    private var newValue = 0
    val count = mutableStateOf(newValue - oldValue)

    init {
        //println(sensorRepository.stepCounterSensorValue.value)
    }

    private fun resetStepCounter() {
        oldValue = newValue
    }

    private fun setStepCounter(value: Int) {
        if (value > newValue) {
            newValue = value
        }
    }

    override suspend fun doWork(): Result {
        Log.e("WM", "Working...")
        return Result.success()
    }
}