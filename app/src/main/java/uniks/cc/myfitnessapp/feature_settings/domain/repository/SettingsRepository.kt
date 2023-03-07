package uniks.cc.myfitnessapp.feature_settings.domain.repository

import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings

interface SettingsRepository {

    // Function to get the current settings from the database
    suspend fun getSettingsFromDatabase(): Settings

    // Function to set the current settings in the database
    suspend fun saveSettingsToDatabase(settings: Settings)
}