package uniks.cc.myfitnessapp.feature_current_workout.data.data_source

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class CardioWorkoutWorker @AssistedInject constructor(
    @Assisted appContext : Context,
    @Assisted params : WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val coreRepository: CoreRepository,
    val locationManager: LocationManager
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        startLocationTracking()
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private suspend fun startLocationTracking() {
        val locationListener = LocationListener { location: Location ->
            val currentWayPoint = Waypoint(workoutRepository.currentWorkout!!.id, Instant.now().epochSecond, location.latitude, location.longitude)
            runBlocking {
                workoutRepository.saveWaypoint(currentWayPoint)
                Log.e("DB", "Saved waypoint $currentWayPoint to database")
            }
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30 * 1000, 0f, locationListener)
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }
}