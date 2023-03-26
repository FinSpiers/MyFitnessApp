package uniks.cc.myfitnessapp.feature_workout.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import kotlin.reflect.KFunction1

interface WorkoutRepository {
    var onWorkoutAction: KFunction1<WorkoutEvent, Unit>
    var workouts: List<Workout>
    var currentWorkout: Workout?
    var selectedWorkoutDetail: Workout?

    val currentWorkoutDistanceStateFlow: MutableStateFlow<String>
    var stopwatchManager: StopwatchManager?

    suspend fun getAllWorkoutsFromDatabase(): List<Workout>

    suspend fun getWorkoutById(workoutId: Int): Workout?

    suspend fun getWorkoutByTimestamp(timestamp: Long): Workout?

    suspend fun addWorkoutToDatabase(workout: Workout)

    suspend fun deleteWorkoutFromDatabase(workout: Workout)

    suspend fun getAllWaypoints(): List<Waypoint>

    suspend fun getWaypointsByWorkoutId(id: Int): List<Waypoint>

    suspend fun saveWaypoint(waypoint: Waypoint)


}