package uniks.cc.myfitnessapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.WorkoutMap
import java.time.Instant

@Entity(tableName = "Workouts")
data class Workout(
    var workoutName: String,
    var timeStamp: Long = Instant.now().epochSecond,
    var duration: String = "00:00:000",
    var kcal: Int = 0,

    // Only Cardiovascular Activities
    var distance: Double? = null,
    var pace: Double? = null,
    var avgPace: Double? = null,

    // Only Weight Activities
    var repetitions: Int? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var imageId: Int = WorkoutMap.map[workoutName]!!
}



