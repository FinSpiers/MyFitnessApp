package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.core.domain.util.hasActivityRecognitionPermission
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class StepCounterSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dashBoardRepository: DashBoardRepository,
    val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        if (applicationContext.hasActivityRecognitionPermission()) {
            return try {
                syncStepCounter()
                Result.success()
            }
            catch (e : Exception) {
                return Result.retry()
            }
        }
        return Result.failure()
    }

    private suspend fun syncStepCounter() {
        sensorRepository.startStepCounterSensor()
        val currentDate = TimestampConverter.convertToDate(Instant.now().epochSecond)
        delay(1000)
        val stepsToday = dashBoardRepository.getDailyStepsByDate(currentDate)
            ?: return
        if (stepsToday.sensorCount.toInt() == 0) {
            val syncedSteps = Steps(sensorRepository.stepCounterSensorValueStateFlow.value, 0, currentDate)
        }
        if (sensorRepository.stepCounterSensorValueStateFlow.value >= stepsToday.sensorCount) {
            val stepsValue = sensorRepository.stepCounterSensorValueStateFlow.value - stepsToday.sensorCount
            val syncedSteps = Steps(stepsValue.toInt(), stepsToday.sensorCount, currentDate)
            dashBoardRepository.saveDailySteps(syncedSteps)
        }
        else {
            // Device restart happened
            val syncedSteps = Steps(sensorRepository.stepCounterSensorValueStateFlow.value, 0)
        }
    }
}