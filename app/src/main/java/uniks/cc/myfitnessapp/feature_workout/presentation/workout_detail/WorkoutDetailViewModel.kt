package uniks.cc.myfitnessapp.feature_workout.presentation.workout_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    var selectedWorkout: Workout = workoutRepository.selectedWorkoutDetail
        ?: throw NullPointerException("Expression 'workoutRepository.selectedWorkoutDetail' must not be null")

    var waypoints : List<Waypoint>

    init {
        runBlocking {
            selectedWorkout = workoutRepository.getWorkoutById(selectedWorkout.id)!!
            waypoints = workoutRepository.getWaypointsByWorkoutId(selectedWorkout.id)
            Log.e("WORKOUT", "Selected workout : ${selectedWorkout.id} with waypoints: $waypoints")
        }
    }

    fun getWaypointsByWorkoutId(id : Int) : List<Waypoint> {
        var res : List<Waypoint> = emptyList()
        runBlocking {
            res = workoutRepository.getWaypointsByWorkoutId(id)
        }
        return res

    }

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
