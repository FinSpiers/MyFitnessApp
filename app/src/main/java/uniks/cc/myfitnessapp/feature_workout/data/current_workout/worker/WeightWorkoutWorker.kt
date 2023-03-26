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
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchStateHolder
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository

@HiltWorker
class WeightWorkoutWorker @AssistedInject constructor(
    @Assisted val appContext : Context,
    @Assisted val params : WorkerParameters,
    private val workoutRepository: WorkoutRepository,
    private val stopwatchManager: StopwatchManager,
) : CoroutineWorker(appContext, params) {

    lateinit var currentWorkout : Workout

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val workoutId = params.inputData.getInt("WORKOUT_ID", -1)
            if (workoutId == -1 || workoutRepository.getWorkoutById(workoutId) == null) {
                return@withContext Result.failure()
            }
            currentWorkout = workoutRepository.getWorkoutById(workoutId)!!

            stopwatchManager.stopAndReset()
            stopwatchManager.start()

            while (true) {
                delay(1000)
                // TODO: Write and call function that counts repetitions automatically
                if (workoutRepository.currentWorkout != null) {
                    break
                }
            }
            currentWorkout.duration = stopwatchManager.ticker.value
            stopwatchManager.stopAndReset()
            workoutRepository.addWorkoutToDatabase(currentWorkout)
            return@withContext Result.success()
        }
        catch (e : Exception) {
            e.printStackTrace()
            return@withContext Result.retry()
        }

    }

}

