package uniks.cc.myfitnessapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_dashboard.data.database.DashboardDao
import uniks.cc.myfitnessapp.feature_settings.data.data_source.database.SettingsDao
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import uniks.cc.myfitnessapp.feature_workout.data.database.WorkoutDao

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

    abstract val dashboardDao : DashboardDao

    abstract val settingsDao : SettingsDao
}