package uniks.cc.myfitnessapp.core.domain.util

import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity
import dagger.hilt.android.AndroidEntryPoint
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import javax.inject.Inject

@AndroidEntryPoint
class ActivityTransitionReceiver : HiltBroadcastReceiver() {
    @Inject
    lateinit var workoutRepository: WorkoutRepository

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = intent?.let { ActivityTransitionResult.extractResult(it) }
            if (workoutRepository.currentWorkout == null) {
                result?.let {
                    result.transitionEvents.forEach { event ->
                        if (event.transitionType == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
                            when (event.activityType) {
                                DetectedActivity.WALKING -> workoutRepository.onWorkoutAction(
                                    WorkoutEvent.StartWorkout("Walking")
                                )
                                DetectedActivity.RUNNING -> workoutRepository.onWorkoutAction(
                                    WorkoutEvent.StartWorkout("Running")
                                )
                                DetectedActivity.ON_BICYCLE -> workoutRepository.onWorkoutAction(
                                    WorkoutEvent.StartWorkout("Bicycling")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
