package uniks.cc.myfitnessapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.WorkoutMap
import java.time.Instant

@Entity(tableName = "DailySteps")
data class Steps(
    var count : Int,
    @PrimaryKey(autoGenerate = false)
    var timeStamp: Long = Instant.now().epochSecond
)



