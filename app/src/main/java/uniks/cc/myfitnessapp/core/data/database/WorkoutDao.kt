package uniks.cc.myfitnessapp.core.data.database

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM Workouts")
    suspend fun getAllWorkouts(): List<Workout>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkout(workout: Workout)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWaypoint(waypoint: Waypoint)

    @Transaction
    @Query("SELECT * FROM TraveledRoute")
    suspend fun getAllWaypoints() : List<Waypoint>


}
