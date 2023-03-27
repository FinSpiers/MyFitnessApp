package uniks.cc.myfitnessapp.feature_workout.data.current_workout.activity_reconition

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity
import dagger.hilt.android.AndroidEntryPoint
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.util.Constants
import uniks.cc.myfitnessapp.core.domain.util.HiltBroadcastReceiver
import uniks.cc.myfitnessapp.core.presentation.MainActivity
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_workout.data.current_workout.worker.CHANNEL_ID
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import javax.inject.Inject

@AndroidEntryPoint
class ActivityTransitionReceiver : HiltBroadcastReceiver() {
    @Inject
    lateinit var workoutRepository: WorkoutRepository

    private val shouldNotify: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = intent?.let { ActivityTransitionResult.extractResult(it) }
            if (workoutRepository.currentWorkout == null) {
                result?.let {
                    result.transitionEvents.forEach { event ->
                        if (event.transitionType == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
                            when (event.activityType) {
                                DetectedActivity.WALKING -> {
                                    workoutRepository.onWorkoutAction(
                                        WorkoutEvent.StartWorkout(Constants.WORKOUT_WALKING)
                                    )
                                    if (shouldNotify) {
                                        buildAndShowWorkoutNotification(
                                            context,
                                            Constants.WORKOUT_WALKING
                                        )
                                    }
                                }
                                DetectedActivity.RUNNING -> {
                                    workoutRepository.onWorkoutAction(
                                        WorkoutEvent.StartWorkout(Constants.WORKOUT_RUNNING)
                                    )
                                    if (shouldNotify) {
                                        buildAndShowWorkoutNotification(
                                            context,
                                            Constants.WORKOUT_RUNNING
                                        )
                                    }
                                }
                                DetectedActivity.ON_BICYCLE -> {
                                    workoutRepository.onWorkoutAction(
                                        WorkoutEvent.StartWorkout(Constants.WORKOUT_BICYCLING)
                                    )
                                    if (shouldNotify) {
                                        buildAndShowWorkoutNotification(
                                            context,
                                            Constants.WORKOUT_BICYCLING
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun buildAndShowWorkoutNotification(context: Context, workoutName: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "CurrentWorkout",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val iconId = when (workoutName) {
            Constants.WORKOUT_WALKING -> R.drawable.image_walking
            Constants.WORKOUT_RUNNING -> R.drawable.image_jogging
            else -> R.drawable.image_bicycling
        }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(iconId)
            .setContentTitle("Workout detected")
            .setContentText("Automatically started $workoutName workout. Click to see details")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
