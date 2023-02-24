package uniks.cc.myfitnessapp.feature_active_workout.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor(
) : ViewModel() {
    /* original code:
    val currentWorkout: Workout = workoutRepository.currentWorkout
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")
    */
    // for testing:
    /*
    val currentWorkout: Workout = Workout(
        workoutName = "PushUps",
        timeStamp = Instant.now().epochSecond,
        duration = 2.5,
        kcal = 21342,
        repetitions = 21
    )
     */
    val currentWorkout: Workout = Workout(
        workoutName = "Walking",
        timeStamp = Instant.now().epochSecond,
        duration = 2.5,
        kcal = 21342,
        distance = 23.4,
        pace = 6.3
    )


}