package uniks.cc.myfitnessapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.model.sensors.Waypoint

@Database(
    entities = [
        Workout::class,
        Waypoint::class
    ],
    version = 1
)
abstract class MyFitnessDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao

    //abstract val settingsDao : SettingsDao
}