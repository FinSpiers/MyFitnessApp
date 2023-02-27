package uniks.cc.myfitnessapp.feature_active_workout.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import javax.inject.Inject


@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor(
    coreRepository: CoreRepository
) : ViewModel() {
    val currentWorkout: Workout = coreRepository.currentWorkout
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")
}