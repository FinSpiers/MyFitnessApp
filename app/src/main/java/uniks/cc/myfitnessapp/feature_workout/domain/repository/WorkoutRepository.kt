package uniks.cc.myfitnessapp.feature_workout.domain.repository

import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity

interface WorkoutRepository {
    var currentWorkout: SportActivity?
    var workouts: MutableList<SportActivity>

    suspend fun getAllSportActivitiesFromDatabase(): List<SportActivity>

    suspend fun addSportActivityToDatabase(sportActivity: SportActivity)

    suspend fun getSportActivityById(id: Int): SportActivity?

}