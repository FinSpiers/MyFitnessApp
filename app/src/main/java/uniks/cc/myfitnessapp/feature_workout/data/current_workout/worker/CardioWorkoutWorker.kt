@file:OptIn(DelicateCoroutinesApi::class)

package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.feature_workout.data.current_workout.calculator.DistanceCalculator
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.Instant

@HiltWorker
class CardioWorkoutWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val params: WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val coreRepository: CoreRepository,
    private val locationManager: LocationManager,
    private val stopwatchManager: StopwatchManager
) : CoroutineWorker(appContext, params) {

    lateinit var locationListener: LocationListener
    override suspend fun doWork(): Result {
        try {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            initLocationListener()
            coreRepository.checkLocationPermission()
            delay(500)

            startStopwatch()
            if (coreRepository.isLocationPermissionGranted && locationManager.isLocationEnabled) {
                startLocationTracking()
                if (workoutRepository.currentWorkout != null) {
                    delay(500)
                    Looper.loop()
                }
                stopListener()
            }
            Looper.myLooper()?.quitSafely()
            return Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }

    }

    private fun stopListener() {
        locationManager.removeUpdates(locationListener)
    }


    private fun initLocationListener() {
        locationListener = LocationListener { location: Location ->
            val currentWorkout = workoutRepository.currentWorkout
            if (currentWorkout != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    val currentWayPoint = Waypoint(
                        currentWorkout.id,
                        Instant.now().epochSecond,
                        location.latitude,
                        location.longitude
                    )
                    workoutRepository.saveWaypoint(currentWayPoint)
                    Log.e("WORK", "Created waypoint")
                }
            }
        }
    }

    private fun startStopwatch() {
        stopwatchManager.start()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationTracking() {
        if (Looper.myLooper() == null) {
            Looper.prepare()
        }
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                15 * 1000,
                0f,
                locationListener
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}