package uniks.cc.myfitnessapp.core.domain.model.sport_activities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sportActivities")
sealed class SportActivity(
    @PrimaryKey(autoGenerate = false)
    val timestamp : Long = System.currentTimeMillis()
) {
    // TODO: Add traveled route (list of locations?) and pace per km (list of doubles?) to parameters
    class WalkingHiking(var duration : Double = 0.0, var distance: Double = 0.0, var avgPace : Double = 0.0, var kcal : Int = 0) : SportActivity()
    class Running(var duration : Double = 0.0, var distance: Double = 0.0, var avgPace : Double = 0.0, var kcal : Int = 0) : SportActivity()
    class BicycleRiding(var duration : Double = 0.0, var distance: Double = 0.0, var avgPace : Double = 0.0, var kcal : Int = 0) : SportActivity()

    class PushUp(var duration: Double = 0.0, var repeats: Int = 0, var kcal: Int = 0) : SportActivity()
    class Squat(var duration: Double = 0.0, var repeats: Int = 0, var kcal: Int = 0) : SportActivity()
    class SitUp(var duration: Double = 0.0, var repeats: Int = 0, var kcal: Int = 0) : SportActivity()

}
