package uniks.cc.myfitnessapp.core.domain.model.sport_activities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.feature_dashboard.data.WorkoutInfoText
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "SportActivities")
open class SportActivity(
    var workoutId: Int,
    var timeStamp: Long = Instant.now().epochSecond
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    // TODO: Add traveled route (list of locations?) and pace per km (list of doubles?) to parameters
    @Entity(tableName = "WalkingActivities")
    data class WalkingHiking(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(0) {
        var workoutName: String = "Walking"
        var imageId: Int = R.drawable.image_walking
    }

    @Entity(tableName = "RunningActivities")
    data class Running(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(1) {
        var workoutName: String = "Running"
        var imageId: Int = R.drawable.image_jogging
    }

    @Entity(tableName = "BicycleRidingActivities")
    data class BicycleRiding(
        var duration: Double = 0.0,
        var distance: Double = 0.0,
        var pace: Double = 0.0,
        var avgPace: Double = 0.0,
        var kcal: Int = 0
    ) : SportActivity(2) {
        var workoutName: String = "BicycleRiding"
        var imageId: Int = R.drawable.image_bicycling
    }


    @Entity(tableName = "PushUpActivities")
    data class PushUp(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(3) {
        var workoutName: String = "PushUps"
        var imageId: Int = R.drawable.image_push_up
    }

    @Entity(tableName = "SquatActivities")
    data class Squat(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(4) {
        var workoutName: String = "Squats"
        var imageId: Int = R.drawable.image_squat
    }

    @Entity(tableName = "SitUpActivities")
    data class SitUp(
        var duration: Double = 0.0,
        var repeats: Int = 0,
        var kcal: Int = 0
    ) : SportActivity(5) {
        var workoutName: String = "SitUps"
        var imageId: Int = R.drawable.image_sit_up
    }

}

fun getWorkoutInfo(sportActivity: SportActivity): WorkoutInfoText {
    return when (sportActivity.workoutId) {
        0 -> {
            val activity = (sportActivity as SportActivity.WalkingHiking)
            WorkoutInfoText(
                activity.imageId,
                activity.workoutName,
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(activity.timeStamp),
                    TimeZone.getDefault().toZoneId()
                ),
                (activity.distance.toString() + " km Hiking")
            )
        }
        1 -> {
            val activity = (sportActivity as SportActivity.Running)
            WorkoutInfoText(
                activity.imageId,
                activity.workoutName,
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(activity.timeStamp),
                    TimeZone.getDefault().toZoneId()
                ),
                (activity.distance.toString() + " km Running")
            )
        }
        2 -> {
            val activity = (sportActivity as SportActivity.BicycleRiding)
            WorkoutInfoText(
                activity.imageId,
                activity.workoutName,
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(activity.timeStamp),
                    TimeZone.getDefault().toZoneId()
                ),
                (activity.distance.toString() + " km Bicycle Riding")
            )
        }
        3 -> {
            val activity = (sportActivity as SportActivity.PushUp)
            WorkoutInfoText(
                activity.imageId,
                activity.workoutName,
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(activity.timeStamp),
                    TimeZone.getDefault().toZoneId()
                ),
                (activity.repeats.toString() + " Push-Ups")
            )
        }
        4 -> {
            val activity = (sportActivity as SportActivity.Squat)
            WorkoutInfoText(
                activity.imageId,
                activity.workoutName,
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(activity.timeStamp),
                    TimeZone.getDefault().toZoneId()
                ),
                (activity.repeats.toString() + " Squats")
            )
        }
        5 -> {
            val activity = (sportActivity as SportActivity.SitUp)
            WorkoutInfoText(
                activity.imageId,
                activity.workoutName,
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(activity.timeStamp),
                    TimeZone.getDefault().toZoneId()
                ),
                (activity.repeats.toString() + " Sit-Ups")
            )
        }
        else -> WorkoutInfoText(0, "ERROR", LocalDateTime.now(), "ERROR")
    }
}
