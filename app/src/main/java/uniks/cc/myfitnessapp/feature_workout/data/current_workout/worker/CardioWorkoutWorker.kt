package uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
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
    private val locationManager: LocationManager,
    private val stopwatchManager: StopwatchManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : CoroutineWorker(appContext, params) {

    lateinit var currentWorkout: Workout
    var locationClient: LocationClient = LocationClientImpl(appContext, fusedLocationProviderClient)
    private val waypoints = mutableListOf<Waypoint>()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            stopwatchManager.stopAndReset()

            val workoutId = params.inputData.getInt("WORKOUT_ID", -1)
            if (workoutId == -1 || workoutRepository.getWorkoutById(workoutId) == null) {
                return@withContext Result.failure()
            }
            currentWorkout = workoutRepository.getWorkoutById(workoutId)!!

            //setForeground(getForegroundInfo())

            startStopwatch()
            locationClient
                .getLocationUpdates(10 * 1000)
                .catch { e -> e.printStackTrace() }
                .onEach { location ->
                    val lat = location.latitude
                    val lon = location.longitude
                    val waypoint = Waypoint(currentWorkout.id, Instant.now().epochSecond, lat, lon)
                    workoutRepository.saveWaypoint(waypoint)
                    waypoints.add(waypoint)
                    Log.e("LOCATION", "Waypoint [${currentWorkout.id}, ($lat, $lon)] saved to database with elapsed time ${stopwatchManager.ticker.value}")
                }
                .launchIn(this)

            if (workoutRepository.currentWorkout != null) {
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                delay(500)
                Looper.loop()
            }
            locationClient.stopLocationUpdates()
            currentWorkout.duration = stopwatchManager.ticker.value
            workoutRepository.addWorkoutToDatabase(currentWorkout)
            Looper.myLooper()?.quitSafely()
            return@withContext Result.success()

        } catch (e: Exception) {
            //e.printStackTrace()
            return@withContext Result.failure()
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
        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.image_jogging)
            .setContentTitle("Workout detected")
            .setContentText("Click to open details")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        return ForegroundInfo(1, notification)
    }

    private fun startStopwatch() {
        stopwatchManager.start()
    }
}