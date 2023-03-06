package uniks.cc.myfitnessapp.feature_current_workout.data.data_source

import android.content.Context
import android.location.LocationManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository

@HiltWorker
class CardioWorkoutWorker @AssistedInject constructor(
    @Assisted appContext : Context,
    @Assisted params : WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val locationManager: LocationManager
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        startLocationTracking()
        return Result.success()
    }

    private fun startLocationTracking() {

    }
}