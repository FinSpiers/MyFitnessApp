package uniks.cc.myfitnessapp.feature_dashboard.data

import androidx.room.*
import uniks.cc.myfitnessapp.core.domain.model.Steps

@Dao
interface DashboardDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDailySteps(steps: Steps)

    @Transaction
    @Query("SELECT * FROM DailySteps")
    suspend fun getAllDailySteps(): List<Steps>

    @Transaction
    @Query("SELECT * FROM DailySteps WHERE date=:pDate")
    suspend fun getDailyStepsByDate(pDate: String): Steps?
}