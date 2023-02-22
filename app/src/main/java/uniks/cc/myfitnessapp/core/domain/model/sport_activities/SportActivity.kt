package uniks.cc.myfitnessapp.core.domain.model.sport_activities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.R
import java.time.Instant
@Entity(tableName = "SportActivities")
abstract class SportActivity(
    var workoutId: Int,
    @PrimaryKey(autoGenerate = false)
    var timeStamp: Long = Instant.now().epochSecond
) {
    // TODO: Add traveled route (list of locations?) and pace per km (list of doubles?) to parameters
    @Entity(tableName = "SportActivities")
    data class WalkingHiking(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(0) {
        val workoutName: String = "Walking"
        val imageId: Int = R.drawable.image_walking
    }

    @Entity(tableName = "SportActivities")
    data class Running(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(1) {
        val workoutName: String = "Running"
        val imageId: Int = R.drawable.image_jogging
    }
    @Entity(tableName = "SportActivities")
    data class BicycleRiding(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(2) {
        val workoutName: String = "BicycleRiding"
        val imageId: Int = R.drawable.image_bicycling
    }
    @Entity(tableName = "SportActivities")
    data class PushUp(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(3) {
        val workoutName: String = "PushUps"
        val imageId: Int = R.drawable.image_push_up
    }
    @Entity(tableName = "SportActivities")
    data class Squat(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(4) {
        val workoutName: String = "Squats"
        val imageId: Int = R.drawable.image_squat
    }
    @Entity(tableName = "SportActivities")
    data class SitUp(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(5) {
        val workoutName: String = "SitUps"
        val imageId: Int = R.drawable.image_sit_up
    }

}
