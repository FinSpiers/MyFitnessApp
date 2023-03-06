package uniks.cc.myfitnessapp.feature_dashboard.data.repository

import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.data.database.WorkoutDao
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import java.time.Instant
import kotlin.reflect.KFunction1

class WorkoutRepositoryImpl(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override lateinit var onWorkoutAction: KFunction1<WorkoutEvent, Unit>
    override var workouts: List<Workout> = emptyList()
    override var currentWorkout: Workout? = null
    override var selectedWorkoutDetail: Workout? = null
    override var oldStepsValue: Int = 0

    init {
        runBlocking {
            oldStepsValue = getDailyStepsByDate(
                TimestampConverter.convertToDate(Instant.now().epochSecond - 60 * 60 * 24)
            )?.count ?: 0
        }
    }

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

    override suspend fun saveDailySteps(steps: Steps) {
        workoutDao.saveDailySteps(steps)
    }

    override suspend fun getDailyStepsByDate(date: String): Steps? {
        return workoutDao.getDailyStepsByDate(date)
    }

    override suspend fun getAllDailySteps(): List<Steps> {
        return workoutDao.getAllDailySteps()
    }



    init {
        suspend {
            workouts = workoutDao.getAllWorkouts()
        }
    }
}