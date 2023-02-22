package uniks.cc.myfitnessapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity

@Database(
    entities = [
        SportActivity::class
    ],
    version = 1
)
abstract class MyFitnessDatabase : RoomDatabase() {

    //abstract val SportActivitiesDao : SportActivitiesDao

    //abstract val settingsDao : SettingsDao
}