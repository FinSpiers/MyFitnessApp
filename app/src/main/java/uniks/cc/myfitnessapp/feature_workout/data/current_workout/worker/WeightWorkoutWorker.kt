package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker

import android.content.Context
import android.os.Looper
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository

@HiltWorker
class WeightWorkoutWorker @AssistedInject constructor(
    @Assisted val appContext : Context,
    @Assisted val params : WorkerParameters,
    private val workoutRepository: WorkoutRepository,
    private val stopwatchManager: StopwatchManager,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            var currentWorkout : Workout? = null
            stopwatchManager.stopAndReset()
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            stopwatchManager.start()
            if (workoutRepository.currentWorkout != null) {
                if (currentWorkout == null) {
                    currentWorkout = workoutRepository.currentWorkout
                }
                delay(500)
                Looper.loop()
            }
            if (currentWorkout != null) {
                currentWorkout.duration = stopwatchManager.ticker.value
                workoutRepository.addWorkoutToDatabase(currentWorkout)
            }
            stopwatchManager.stopAndReset()
            Looper.myLooper()?.quitSafely()
            return@coroutineScope Result.success()
        }
        catch (e : Exception) {
            e.printStackTrace()
            return@coroutineScope Result.retry()
        }

    }

}

