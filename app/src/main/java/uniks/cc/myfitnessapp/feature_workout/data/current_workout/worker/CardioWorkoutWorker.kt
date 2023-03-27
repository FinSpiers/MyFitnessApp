package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.Constants
import uniks.cc.myfitnessapp.core.domain.util.hasLocationPermission
import uniks.cc.myfitnessapp.core.presentation.MainActivity
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.location_client.LocationClient
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.location_client.LocationClientImpl
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.Instant
import java.util.LinkedList
import java.util.Queue

const val CHANNEL_ID = "CURRENT_WORKOUT"

@HiltWorker
class CardioWorkoutWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val params: WorkerParameters,
    val workoutRepository: WorkoutRepository,
    val coreRepository: CoreRepository,
    private val stopwatchManager: StopwatchManager,
    fusedLocationProviderClient: FusedLocationProviderClient
) : CoroutineWorker(appContext, params) {

    lateinit var currentWorkout: Workout
    private var locationClient: LocationClient =
        LocationClientImpl(appContext, fusedLocationProviderClient)
    private lateinit var locationFlow: Flow<Location>

    private var lastLocation: Location? = null
    private var currentLocation: Location? = null
    private var movedDistance = 0
    private var currentPace = 0.0

    private val waypointQueue = LinkedList<Waypoint>()

    override suspend fun doWork(): Result = coroutineScope {
        try {
            stopwatchManager.stopAndReset()

            val workoutId = params.inputData.getInt("WORKOUT_ID", -1)
            if (workoutId == -1 || workoutRepository.getWorkoutById(workoutId) == null) {
                return@coroutineScope Result.retry()
            }
            currentWorkout = workoutRepository.getWorkoutById(workoutId)!!

            startStopwatch()

            if (appContext.hasLocationPermission()) {
                locationFlow = locationClient.getLocationUpdates(10 * 1000)

                locationFlow.catch { e -> e.printStackTrace() }
                    .onEach { location ->
                        if (currentLocation != null) {
                            lastLocation = currentLocation
                            currentLocation = location
                        } else {
                            currentLocation = location
                        }
                        val lat = location.latitude
                        val lon = location.longitude
                        val waypoint =
                            Waypoint(currentWorkout.id, Instant.now().epochSecond, lat, lon)

                        workoutRepository.saveWaypoint(waypoint)

                        delay(250)
                        // TODO Delete
                        Log.e(
                            "DB",
                            workoutRepository.getWaypointsByWorkoutId(currentWorkout.id).toString()
                        )

                        waypointQueue.add(waypoint)
                    }
                    .launchIn(this)
            } else {
                workoutRepository.onError(
                    appContext.getString(R.string.warning_no_gps_permission_title),
                    appContext.getString(R.string.warning_no_gps_permission_text)
                )
            }
            while (true) {
                delay(1000)
                calculateDistance()
                currentWorkout = currentWorkout.apply {
                    duration = stopwatchManager.ticker.value
                    distance = movedDistance.toDouble()
                    pace = currentPace
                }
                if (workoutRepository.currentWorkout == null)
                    break
            }
            calculateDistance()

            locationClient.stopLocationUpdates()
            currentWorkout.duration = stopwatchManager.ticker.value
            workoutRepository.addWorkoutToDatabase(currentWorkout)
            stopwatchManager.stopAndReset()

            return@coroutineScope Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            workoutRepository.onError("WARNING! CRITICAL ERROR DETECTED!", "Please restart the current workout by clicking here")
            return@coroutineScope Result.failure()

        }

    }


    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "CurrentWorkout",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val iconId = when (currentWorkout.workoutName) {
            Constants.WORKOUT_WALKING -> R.drawable.image_walking
            Constants.WORKOUT_RUNNING -> R.drawable.image_jogging
            else -> R.drawable.image_bicycling
        }
        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(iconId)
            .setContentTitle("Workout detected")
            .setContentText("Automatically started ${currentWorkout.workoutName} workout. Click to see details")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        return ForegroundInfo(1, notification)
    }

    private fun startStopwatch() {
        stopwatchManager.start()
    }

    private fun calculateDistance() {
        if (waypointQueue.isNotEmpty()) {
            val waypoint = waypointQueue.pop()
            val location = Location("waypoint").apply {
                latitude = waypoint.locationLat
                longitude = waypoint.locationLon
            }
            if (currentLocation == null) {
                currentLocation = location
            } else {
                lastLocation = currentLocation
                currentLocation = location
            }
            val last = lastLocation
            val current = currentLocation
            if (last != null && current != null) {
                movedDistance += last.distanceTo(current).toInt()
            }
        }
    }
}