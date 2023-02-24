package uniks.cc.myfitnessapp.feature_workout_detail.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_active_workout.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    private var currentSportActivity = workoutRepository.currentWorkout
    var model : Workout = workoutRepository.selectedWorkoutDetail
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")


}
