package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Looper
import android.util.Log
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
import uniks.cc.myfitnessapp.core.presentation.MainActivity
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.location_client.LocationClient
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.location_client.LocationClientImpl
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import java.time.Instant

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
    private val waypoints = mutableListOf<Waypoint>()
    private lateinit var locationFlow : Flow<Location>

    override suspend fun doWork(): Result = coroutineScope {  //withContext(Dispatchers.IO) {
        try {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            stopwatchManager.stopAndReset()

            val workoutId = params.inputData.getInt("WORKOUT_ID", -1)
            if (workoutId == -1 || workoutRepository.getWorkoutById(workoutId) == null) {
                return@coroutineScope Result.retry() //@withContext Result.failure()
            }
            currentWorkout = workoutRepository.getWorkoutById(workoutId)!!

            startStopwatch()
            locationFlow = locationClient.getLocationUpdates(10 * 1000).cancellable()

            locationFlow.catch { e -> e.printStackTrace() }
                .onEach { location ->
                    val lat = location.latitude
                    val lon = location.longitude
                    val waypoint = Waypoint(currentWorkout.id, Instant.now().epochSecond, lat, lon)
                    workoutRepository.saveWaypoint(waypoint)
                    waypoints.add(waypoint)
                    Log.e(
                        "LOCATION",
                        "Waypoint [${currentWorkout.id}, ($lat, $lon)] saved to database with elapsed time ${stopwatchManager.ticker.value}"
                    )
                }
                .launchIn(this)
            delay(1000)
            Log.e("STATUS", workoutRepository.currentWorkout.toString())
            if (workoutRepository.currentWorkout != null) {
                delay(500)
                Looper.loop()
            }

            locationClient.stopLocationUpdates()
            Log.e("LocClient", "Stopped Location updates")
            currentWorkout.duration = stopwatchManager.ticker.value
            workoutRepository.addWorkoutToDatabase(currentWorkout)
            Log.e("DB", "Saved $currentWorkout to database")
            stopwatchManager.stopAndReset()
            //onCancellation()

            Looper.myLooper()?.quitSafely()
            return@coroutineScope Result.success() //@withContext Result.success()

        } catch (e: Exception) {
            //e.printStackTrace()
            return@coroutineScope Result.failure() //@withContext Result.failure()
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

    private suspend fun onCancellation() {


    }
}