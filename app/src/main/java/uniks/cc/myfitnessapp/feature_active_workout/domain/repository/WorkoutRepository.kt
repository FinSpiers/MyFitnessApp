package uniks.cc.myfitnessapp.feature_active_workout.domain.repository

import uniks.cc.myfitnessapp.core.domain.model.Workout

interface WorkoutRepository {
    var selectedWorkoutDetail : Workout?
    var currentWorkout: Workout?

    suspend fun getAllSportActivitiesFromDatabase(): List<Workout>

    suspend fun addSportActivityToDatabase(workout: Workout)

}