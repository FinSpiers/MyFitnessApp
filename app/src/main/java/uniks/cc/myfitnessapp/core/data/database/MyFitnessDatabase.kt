package uniks.cc.myfitnessapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_settings.data.data_source.database.SettingsDao
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings

@Database(
    entities = [
        Workout::class,
        Settings::class,
        Waypoint::class,
        Steps::class
    ],
    version = 1
)
abstract class MyFitnessDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao

    abstract val settingsDao : SettingsDao
}