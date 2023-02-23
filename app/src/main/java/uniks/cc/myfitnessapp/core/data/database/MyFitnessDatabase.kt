package uniks.cc.myfitnessapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.feature_settings.data.data_source.database.SettingsDao
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings

@Database(
    entities = [
        SportActivity::class,
        SportActivity.WalkingHiking::class,
        SportActivity.BicycleRiding::class,
        SportActivity.Running::class,
        SportActivity.PushUp::class,
        SportActivity.SitUp::class,
        SportActivity.Squat::class,
        Settings::class
    ],
    version = 1
)
abstract class MyFitnessDatabase : RoomDatabase() {

    abstract val sportActivitiesDao: SportActivitiesDao

    abstract val settingsDao : SettingsDao
}