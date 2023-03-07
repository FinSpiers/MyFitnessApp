package uniks.cc.myfitnessapp.feature_settings.data.repository

import android.util.Log
import uniks.cc.myfitnessapp.feature_settings.data.data_source.database.SettingsDao
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDao: SettingsDao
) : SettingsRepository {

    // Function to get the current settings from the database
    override suspend fun getSettingsFromDatabase(): Settings {
        return settingsDao.getSettings() ?: Settings()
    }

    // Function to set the current settings in the database
    override suspend fun saveSettingsToDatabase(settings: Settings) {
        try {
            settingsDao.setSettings(settings)
        } catch (e: Exception) {
            Log.e("DB", "Error on saving in database: ${e.stackTrace}")
        }
    }
}