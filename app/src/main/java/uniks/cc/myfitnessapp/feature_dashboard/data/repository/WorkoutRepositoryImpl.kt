package uniks.cc.myfitnessapp.feature_dashboard.data.repository

import uniks.cc.myfitnessapp.core.data.database.WorkoutDao
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import kotlin.reflect.KFunction1

class WorkoutRepositoryImpl(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override lateinit var onWorkoutAction: KFunction1<WorkoutEvent, Unit>
    override var workouts: List<Workout> = emptyList()
    override var currentWorkout: Workout? = null
    override var selectedWorkoutDetail: Workout? = null
    override suspend fun getAllWorkoutsFromDatabase(): List<Workout> {
        return workoutDao.getAllWorkouts()
    }

    override suspend fun getWorkoutById(workoutId: Int): Workout? {
        return workoutDao.getWorkoutById(workoutId)
    }

    override suspend fun addWorkoutToDatabase(workout: Workout) {
        workoutDao.addWorkout(workout)
    }

    override suspend fun deleteWorkoutFromDatabase(workout: Workout) {
        return workoutDao.deleteWorkout(workout)
    }

    init {
        suspend {
            workouts = workoutDao.getAllWorkouts()
        }
    }
}