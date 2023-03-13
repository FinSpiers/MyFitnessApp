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
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class StepCounterResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val dashBoardRepository: DashBoardRepository,
    val workoutRepository: WorkoutRepository,
    val sensorRepository: SensorRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        resetStepCounter()
        return Result.success()
    }

    private suspend fun resetStepCounter() {
        // Save today's steps in the database
        saveDailyCount()
        delay(3000)
        // Update oldValue
        val oldValue = dashBoardRepository.getAllDailySteps().sumOf { it.count }
        if (sensorRepository.stepCounterSensorValueStateFlow.value > oldValue) {
            dashBoardRepository.oldStepsValue = oldValue
        }
        else {
            dashBoardRepository.oldStepsValue = 0
        }
    }

    private suspend fun saveDailyCount() {
        val allSteps = dashBoardRepository.getAllDailySteps()

        if (allSteps.isEmpty()) {
            val oldDate = TimestampConverter.convertToDate(Instant.now().epochSecond - 60 * 60 * 24)
            val oldStepsCount = sensorRepository.stepCounterSensorValueStateFlow.value
            dashBoardRepository.saveDailySteps(Steps(oldStepsCount, oldDate))
        }
        else {
            val oldStepsCount = dashBoardRepository.getAllDailySteps().sumOf { it.count }
            dashBoardRepository.saveDailySteps(Steps(sensorRepository.stepCounterSensorValueStateFlow.value - oldStepsCount))
        }
    }
}