@file:OptIn(DelicateCoroutinesApi::class)

package uniks.cc.myfitnessapp.feature_current_workout.data.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch.*
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch.StopwatchOrchestrator
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class CardioWorkoutWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val params: WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val coreRepository: CoreRepository,
    val locationManager: LocationManager,
    val stopwatchOrchestrator: StopwatchOrchestrator
) : CoroutineWorker(appContext, params) {

    lateinit var locationListener: LocationListener
    lateinit var workout: Workout

    init {
        initLocationListener()
    }

    override suspend fun doWork(): Result {
        if (Looper.myLooper() == null)  {
            Looper.prepare()
        }
        startStopwatch()

        //if (coreRepository.isLocationPermissionGranted) {
        startLocationTracking()
        delay(500)
        if (workoutRepository.currentWorkout != null) {
            delay(500)
            Looper.loop()
        }
        stopListener()
        Looper.myLooper()?.quitSafely()

        //}
        return Result.success()
    }

    private fun stopListener() {
        locationManager.removeUpdates(locationListener)
    }


    private fun initLocationListener() {
        locationListener = LocationListener { location: Location ->
            val currentWorkout = workoutRepository.currentWorkout
            if (currentWorkout != null) {
                Log.e("CURRENT_WORKOUT", "ID=$currentWorkout")
                GlobalScope.launch(Dispatchers.IO) {
                    val currentWayPoint = Waypoint(
                        currentWorkout.id,
                        Instant.now().epochSecond,
                        location.latitude,
                        location.longitude
                    )
                    workoutRepository.saveWaypoint(currentWayPoint)
                    Log.e(
                        "DB",
                        "Saved waypoint $currentWayPoint with current workout time of ${stopwatchOrchestrator.ticker.value} to database"
                    )
                }
            }
        }
    }

    private fun startStopwatch() {
        stopwatchOrchestrator.start()
    }

    fun stopStopwatch() {
        stopwatchOrchestrator.stop()
    }

    fun pause() {
        stopwatchOrchestrator.pause()
    }

    fun getTicker(): String {
        return stopwatchOrchestrator.ticker.value
    }

    @SuppressLint("MissingPermission")
    private fun startLocationTracking() {
        try {

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                30 * 1000,
                0f,
                locationListener
            )

            //locationManager.removeUpdates(locationListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}