package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.core.domain.util.hasActivityRecognitionPermission
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class StepCounterResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dashBoardRepository: DashBoardRepository,
    val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        if (applicationContext.hasActivityRecognitionPermission()) {
            return try {
                resetStepCounter()
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
        return Result.failure()
    }

    private suspend fun resetStepCounter() {
        sensorRepository.startStepCounterSensor()

        val yesterDate = TimestampConverter.convertToDate(Instant.now().epochSecond - 24 * 3600)
        val oldSteps = dashBoardRepository.getDailyStepsByDate(yesterDate)
        val todaySteps: Steps
        if (oldSteps == null) {
            todaySteps = Steps(
                dailyCount = 0,
                sensorCount = sensorRepository.stepCounterSensorValueStateFlow.value.toLong()
            )
        } else {
            todaySteps = Steps(0, oldSteps.sensorCount + oldSteps.dailyCount)
        }
        dashBoardRepository.saveDailySteps(todaySteps)
    }
}