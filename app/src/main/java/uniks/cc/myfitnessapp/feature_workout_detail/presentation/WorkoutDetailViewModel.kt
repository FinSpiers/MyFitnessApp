package uniks.cc.myfitnessapp.feature_workout_detail.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    coreRepository: CoreRepository
) : ViewModel() {
    var selectedWorkout: Workout = coreRepository.selectedWorkoutDetail
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")


}
