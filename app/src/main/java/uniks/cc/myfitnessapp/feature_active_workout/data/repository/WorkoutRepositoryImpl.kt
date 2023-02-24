package uniks.cc.myfitnessapp.feature_active_workout.data.repository

import uniks.cc.myfitnessapp.core.data.database.WorkoutDao
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_active_workout.domain.repository.WorkoutRepository

class WorkoutRepositoryImpl(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override var selectedWorkoutDetail: Workout? = null
    override var currentWorkout: Workout? = null

    override suspend fun getAllSportActivitiesFromDatabase(): List<Workout> {
        return workoutDao.getAllWorkouts()
    }

    override suspend fun addSportActivityToDatabase(workout: Workout) {
        workoutDao.addWorkout(workout)
    }

}