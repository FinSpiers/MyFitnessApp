package uniks.cc.myfitnessapp.core.domain.model.sport_activities

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.R
import java.time.LocalDateTime

@Entity(tableName = "sportActivities")
sealed class SportActivity(
    @PrimaryKey(autoGenerate = false)
    var workoutId: Int,
    val dateTime: LocalDateTime = LocalDateTime.now()

) {
    // TODO: Add traveled route (list of locations?) and pace per km (list of doubles?) to parameters
    class WalkingHiking(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(0) {
        val workoutName: String = "Walking"
        val imageId: Int = R.drawable.image_walking
    }

    class Running(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(1) {
        val workoutName: String = "Running"
        val imageId: Int = R.drawable.image_jogging
    }

    class BicycleRiding(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(2) {
        val workoutName: String = "BicycleRiding"
        val imageId: Int = R.drawable.image_bicycling
    }

    class PushUp(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(3) {
        val workoutName: String = "PushUps"
        val imageId: Int = R.drawable.image_pushUp
    }

    class Squat(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(4) {
        val workoutName: String = "Squats"
        val imageId: Int = R.drawable.image_squat
    }

    class SitUp(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(5) {
        val workoutName: String = "SitUps"
        val imageId: Int = R.drawable.image_sitUp
    }

}
