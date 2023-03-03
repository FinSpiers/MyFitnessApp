package uniks.cc.myfitnessapp.feature_current_workout.data.data_source

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant
import javax.inject.Inject

@HiltWorker
class StepCounterWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val sensorRepository: SensorRepository
) : CoroutineWorker(context, workerParams) {


    private var oldValue = 0
    private var newValue = 0
    val count = mutableStateOf(newValue - oldValue)

    init {
        syncStepCounter()
        saveDailyCount()
        sensorRepository.startStepCounterSensor()
        println(sensorRepository.stepCounterSensorValue)
    }

    private fun resetStepCounter() {
        saveDailyCount()
        oldValue = newValue
    }

    private fun saveDailyCount() {
        println(Steps(count.value))
        //suspend {
        //    workoutRepository.saveDailySteps(Steps(count.value))
        //}
    }


    private fun syncStepCounter() {
        newValue = sensorRepository.stepCounterSensorValue
    }



    override suspend fun doWork(): Result {
        Log.e(
            "WM",
            "${TimestampConverter.convertToDatetime(Instant.now().epochSecond)}, Counting ${count.value}}"
        )
        return Result.success()
    }


}