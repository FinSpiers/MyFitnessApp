package uniks.cc.myfitnessapp.core.data.database

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity

@Dao
interface SportActivitiesDao {
    @Transaction
    @Query("SELECT * FROM SportActivities")
    suspend fun getAllSportActivities(): SportActivity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSportActivity(sportActivity: SportActivity)
}