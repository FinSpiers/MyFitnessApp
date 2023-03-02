package uniks.cc.myfitnessapp.core.data.database

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.model.sensors.Waypoint

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM Workouts")
    suspend fun getAllWorkouts(): List<Workout>

    @Transaction
    @Query("SELECT * FROM Workouts WHERE id=:workoutId")
    suspend fun getWorkoutById(workoutId : Int) : Workout?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkout(workout: Workout)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWaypoint(waypoint: Waypoint)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Transaction
    @Query("SELECT * FROM TraveledRoute")
    suspend fun getAllWaypoints() : List<Waypoint>


}
