package uniks.cc.myfitnessapp.core.domain.model.sensors

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TraveledRoute")
data class Waypoint(
    var workoutId : Int,
    var timeStamp: Long,
    var locationLat : Double,
    var locationLon : Double
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}