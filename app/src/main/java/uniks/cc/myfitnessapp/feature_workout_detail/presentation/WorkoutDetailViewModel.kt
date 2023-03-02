package uniks.cc.myfitnessapp.feature_workout_detail.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    var selectedWorkout: Workout = workoutRepository.selectedWorkoutDetail
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")

    fun onNavigationAction(navigationEvent: NavigationEvent) {
        coreRepository.onNavigationAction(navigationEvent)
    }

    fun onWorkoutAction(workoutEvent: WorkoutEvent) {
        workoutRepository.onWorkoutAction(workoutEvent)
    }

    fun hasCurrentWorkout() : Boolean {
        return workoutRepository.currentWorkout != null
    }
}
