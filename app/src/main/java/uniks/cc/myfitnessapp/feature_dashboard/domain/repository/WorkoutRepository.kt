package uniks.cc.myfitnessapp.feature_dashboard.domain.repository

import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.presentation.WorkoutEvent
import kotlin.reflect.KFunction1

interface WorkoutRepository {
    var onWorkoutAction : KFunction1<WorkoutEvent, Unit>
    var workouts : List<Workout>
    var currentWorkout: Workout?
    var selectedWorkoutDetail : Workout?
    var oldStepsValue : Int

    suspend fun getAllWorkoutsFromDatabase() : List<Workout>

    suspend fun getWorkoutById(workoutId : Int) : Workout?

    suspend fun addWorkoutToDatabase(workout: Workout)

    suspend fun deleteWorkoutFromDatabase(workout: Workout)

    suspend fun saveDailySteps(steps: Steps)

    suspend fun getDailyStepsByDate(date : String) : Steps?
    suspend fun getAllDailySteps() : List<Steps>


}