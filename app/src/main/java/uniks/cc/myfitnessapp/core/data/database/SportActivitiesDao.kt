package uniks.cc.myfitnessapp.core.data.database

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity

@Dao
interface SportActivitiesDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWalkingActivity(walkingHiking: SportActivity.WalkingHiking)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRunningActivity(running: SportActivity.Running)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBicyclingActivity(bicycleRiding: SportActivity.BicycleRiding)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPushUpActivity(pushUp: SportActivity.PushUp)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSitUpActivity(sitUp: SportActivity.SitUp)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSquatsActivity(squat: SportActivity.Squat)

    //@Transaction
    //@Query("SELECT * FROM WalkingActivities, RunningActivities, BicycleRidingActivities, PushUpActivities, SquatActivities, SitUpActivities")
    //suspend fun getAllSportActivities(): List<SportActivity>

    @Transaction
    @Query("SELECT * FROM WalkingActivities")
    suspend fun getAllWalkingActivities() : List<SportActivity.WalkingHiking>

    @Transaction
    @Query("SELECT * FROM RunningActivities")
    suspend fun getAllRunningActivities() : List<SportActivity.Running>

    @Transaction
    @Query("SELECT * FROM BicycleRidingActivities")
    suspend fun getAllBicycleActivities() : List<SportActivity.BicycleRiding>

    @Transaction
    @Query("SELECT * FROM PushUpActivities")
    suspend fun getAllPushUpsActivities() : List<SportActivity.PushUp>

    @Transaction
    @Query("SELECT * FROM SquatActivities")
    suspend fun getAllSquatsActivities() : List<SportActivity.Squat>

    @Transaction
    @Query("SELECT * FROM SitUpActivities")
    suspend fun getAllSitUpActivities() : List<SportActivity.SitUp>
}
