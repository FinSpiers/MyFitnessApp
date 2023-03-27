package uniks.cc.myfitnessapp.feature_workout.data.repository

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import uniks.cc.myfitnessapp.feature_workout.data.database.WorkoutDao
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.StopwatchManager
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import kotlin.reflect.KFunction1

class WorkoutRepositoryImpl(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override lateinit var onWorkoutAction: KFunction1<WorkoutEvent, Unit>
    override var workouts: List<Workout> = emptyList()
    override var currentWorkout: Workout? = null
    override var selectedWorkoutDetail: Workout? = null

    override val currentWorkoutDistanceStateFlow: MutableStateFlow<String> = MutableStateFlow("-")
    override var stopwatchManager: StopwatchManager? = null
    override var hasError = mutableStateOf(false)
    override var errorTitle= mutableStateOf("")
    override var errorText = mutableStateOf("")
    override var onError: (String, String) -> Unit = {s, s2 -> }


    override suspend fun getAllWorkoutsFromDatabase(): List<Workout> {
        return workoutDao.getAllWorkouts()
    }

    override suspend fun getWorkoutById(workoutId: Int): Workout? {
        return workoutDao.getWorkoutById(workoutId)
    }

    override suspend fun getWorkoutByTimestamp(timestamp: Long): Workout? {
        return workoutDao.getWorkoutByTimestamp(timestamp)
    }

    override suspend fun addWorkoutToDatabase(workout: Workout) {
        workoutDao.addWorkout(workout)
    }

    override suspend fun deleteWorkoutFromDatabase(workout: Workout) {
        return workoutDao.deleteWorkout(workout)
    }

    override suspend fun getAllWaypoints(): List<Waypoint> {
        return workoutDao.getAllWaypoints()
    }

    override suspend fun getWaypointsByWorkoutId(id: Int): List<Waypoint> {
        return workoutDao.getWaypointsByWorkoutId(id)
    }

    override suspend fun saveWaypoint(waypoint: Waypoint) {
        workoutDao.addWaypoint(waypoint)
    }
}