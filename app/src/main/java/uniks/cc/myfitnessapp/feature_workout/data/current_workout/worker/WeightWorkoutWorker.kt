package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker

import android.content.Context
import android.os.Looper
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchStateHolder
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository

@HiltWorker
class WeightWorkoutWorker @AssistedInject constructor(
    @Assisted val appContext : Context,
    @Assisted val params : WorkerParameters,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository,
    private val stopwatchManager: StopwatchManager,
) : CoroutineWorker(appContext, params) {

    lateinit var currentWorkout : Workout

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val workoutId = params.inputData.getInt("WORKOUT_ID", -1)
            if (workoutId == -1 || workoutRepository.getWorkoutById(workoutId) == null) {
                return@coroutineScope Result.retry()
            }
            currentWorkout = workoutRepository.getWorkoutById(workoutId)!!
            stopwatchManager.stopAndReset()
            sensorRepository.startAccelerometerSensor()
            stopwatchManager.start()
            while (true) {
                delay(1000)
                Log.e("SENSOR", "x: ${sensorRepository.accelerometerSensorValueX}, y: ${sensorRepository.accelerometerSensorValueY}, z: ${sensorRepository.accelerometerSensorValueZ}")
                // TODO: Write and call function that counts repetitions automatically
                if (workoutRepository.currentWorkout == null) {
                    break
                }
            }
            currentWorkout.duration = stopwatchManager.ticker.value
            stopwatchManager.stopAndReset()
            sensorRepository.stopAccelerometerSensor()
            workoutRepository.addWorkoutToDatabase(currentWorkout)
            return@coroutineScope Result.success()
        }
        catch (e : Exception) {
            e.printStackTrace()
            sensorRepository.stopAccelerometerSensor()
            return@coroutineScope Result.retry()
        }

    }

}

