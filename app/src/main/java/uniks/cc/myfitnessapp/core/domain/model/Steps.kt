package uniks.cc.myfitnessapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.WorkoutMap
import java.time.Instant

@Entity(tableName = "DailySteps")
data class Steps(
    var count : Int,
    @PrimaryKey(autoGenerate = false)
    var date: String = TimestampConverter.convertToDate(Instant.now().epochSecond)
)



