package uniks.cc.myfitnessapp.feature_settings.data.data_source.database

import androidx.room.*
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings

@Dao
interface SettingsDao {
    @Transaction
    @Query("SELECT * FROM settings")
    suspend fun getSettings(): Settings?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(settings: Settings)
}