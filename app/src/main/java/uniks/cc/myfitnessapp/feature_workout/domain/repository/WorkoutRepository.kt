package uniks.cc.myfitnessapp.feature_workout.domain.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import kotlin.reflect.KFunction1

interface WorkoutRepository {
    var onWorkoutAction : KFunction1<WorkoutEvent, Unit>
    var workouts : List<Workout>
    var currentWorkout: Workout?
    var selectedWorkoutDetail : Workout?
    //val currentWorkoutTimerStateFlow : MutableStateFlow<String>
    val currentWorkoutDistanceStateFlow : MutableStateFlow<String>
    var stopwatchManager : StopwatchManager?

    var hasError : MutableState<Boolean>
    var errorTitle : MutableState<String>
    var errorText : MutableState<String>

    fun clearError()
    fun onError(errorTitle : String, errorText : String)
    suspend fun getAllWorkoutsFromDatabase() : List<Workout>

    suspend fun getWorkoutById(workoutId : Int) : Workout?

    suspend fun getWorkoutByTimestamp(timestamp : Long) : Workout?

    suspend fun addWorkoutToDatabase(workout: Workout)

    suspend fun deleteWorkoutFromDatabase(workout: Workout)

    suspend fun getAllWaypoints() : List<Waypoint>

    suspend fun getWaypointsByWorkoutId(id : Int) : List<Waypoint>

    suspend fun saveWaypoint(waypoint: Waypoint)


}