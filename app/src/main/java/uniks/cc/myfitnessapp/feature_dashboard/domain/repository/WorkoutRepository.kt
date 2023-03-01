package uniks.cc.myfitnessapp.feature_dashboard.domain.repository

import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.presentation.WorkoutEvent
import kotlin.reflect.KFunction1

interface WorkoutRepository {
    var onWorkoutAction : KFunction1<WorkoutEvent, Unit>
    var workouts : List<Workout>
    var currentWorkout: Workout?
    var selectedWorkoutDetail : Workout?

    suspend fun getAllWorkoutsFromDatabase() : List<Workout>

    suspend fun addWorkoutToDatabase(workout: Workout)

    suspend fun deleteWorkoutFromDatabase(workout: Workout)


}