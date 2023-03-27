package uniks.cc.myfitnessapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import java.time.Instant

@Entity(tableName = "DailySteps")
data class Steps(
    var dailyCount: Int,
    var sensorCount: Long,
    @PrimaryKey(autoGenerate = false)
    var date: String = TimestampConverter.convertToDate(Instant.now().epochSecond)
)



