package uniks.cc.myfitnessapp.core.data.database

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity

@Dao
interface SportActivitiesDao {
    //@Transaction
    //@Query("SELECT * FROM SportActivities")
    //suspend fun getAllSportActivities(): List<SportActivity>

    //@Transaction
    //@Query("SELECT * FROM SportsActivities WHERE Id={id}")
    //suspend fun getSportActivityById(id : Int) : SportActivity?

    //@Transaction
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //suspend fun addSportActivity(sportActivity: SportActivity)

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
}