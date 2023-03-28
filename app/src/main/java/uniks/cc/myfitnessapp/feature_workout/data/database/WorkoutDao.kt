package uniks.cc.myfitnessapp.feature_workout.data.database

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM Workouts")
    suspend fun getAllWorkouts(): List<Workout>

    @Transaction
    @Query("SELECT * FROM Workouts WHERE id=:workoutId")
    suspend fun getWorkoutById(workoutId: Int): Workout?

    @Transaction
    @Query("SELECT * FROM Workouts WHERE timeStamp=:timestamp")
    suspend fun getWorkoutByTimestamp(timestamp: Long): Workout?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Delete
    suspend fun deleteWaypoint(waypoint: Waypoint)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWaypoint(waypoint: Waypoint)

    @Transaction
    @Query("SELECT * FROM TraveledRoute")
    suspend fun getAllWaypoints(): List<Waypoint>

    @Transaction
    @Query("SELECT * FROM TraveledRoute WHERE workoutId=:id")
    suspend fun getWaypointsByWorkoutId(id: Int): List<Waypoint>


}
