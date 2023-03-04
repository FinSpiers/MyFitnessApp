package uniks.cc.myfitnessapp.feature_current_workout.data.data_source

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class StepCounterResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val sensorRepository: SensorRepository
) : CoroutineWorker(context, workerParams) {

    private suspend fun resetStepCounter() {
        val currentDate = TimestampConverter.convertToDate(Instant.now().epochSecond)
        // Save today's steps in the database
        saveDailyCount()

        // Update oldValue
        val oldValue = workoutRepository.getDailyStepsByDate(currentDate)
        if (oldValue != null) {
            workoutRepository.oldStepsValue = oldValue.count
        }
    }

    private suspend fun saveDailyCount() {
            try {
                val dailySteps = Steps(sensorRepository.stepCounterSensorValueStateFlow.value)
                workoutRepository.saveDailySteps(dailySteps)
            }
            catch (e : Exception) {
                e.printStackTrace()
            }
    }

    override suspend fun doWork(): Result {
        resetStepCounter()
        delay(500)
        Log.e("WORK", "Current State: oldValue=${workoutRepository.oldStepsValue}, newValue=${sensorRepository.stepCounterSensorValueStateFlow.value}")
        return Result.success()
    }


}