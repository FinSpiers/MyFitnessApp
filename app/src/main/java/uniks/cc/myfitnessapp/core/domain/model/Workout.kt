package uniks.cc.myfitnessapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.WorkoutMap
import java.time.Instant

@Entity(tableName = "Workouts")
data class Workout(
    // TODO: Add traveled route (list of locations?) and pace per km (list of doubles?) to parameters
    var workoutName: String,
    var timeStamp: Long = Instant.now().epochSecond,
    var duration: Double = 0.0,
    var kcal: Int = 0,

    // Only Cardiovascular Activities
    var distance: Double? = null,
    var pace: Double? = null,
    var avgPace: Double? = null,

    // Only Weight Activities
    var repetitions : Int? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var imageId : Int = WorkoutMap.map[workoutName]!!
}



